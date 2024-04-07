package com.example.backend.global.authorization.jwt.filter;

import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.authorization.jwt.service.JwtService;
import com.example.backend.global.util.RandomPasswordUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL = "/login"; // "/login"으로 들어오는 요청은 Filter 작동 X

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if (request.getRequestURI().equals(NO_CHECK_URL)) { // "/login" 경로로 요청이 들어오면
            filterChain.doFilter(request, response); // 다음 필터 호출
            // return 으로 이후 현재 필터 진행 막기 (안해주면 아래로 내려가서 계속 필터 진행시킴)
            return;
        }

        // 사용자 요청 헤더에서 RefreshToken 추출
        String refreshToken = jwtService.extractRefreshToken(request) // 생성한 extractRefreshToken 을 사용하여 RefreshToken 추출
                .filter(jwtService::isTokenValid) // // 유효 하지 않거나
                .orElse(null); // 존재 하지 않으면 null 반환


        if (refreshToken != null) { // RefreshToken 이 요청 헤더에 존재하면 AccessToken 이 만료되어 RefreshToken 이 넘어온 것이므로
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken); // DB에 저장된 RefreshToken 과 요청헤더에 들어온 RefreshToken 을 비교하여 유저를 찾는다.
            // 유저를 찾으면 AccessToken 과 RefreshToken 을 재발급 해준다.
            return; // RefreshToken 을 보낸 경우에는 AccessToken 을 재발급 하고 인증 처리는 하지 않게 하기위해 바로 return  으로 필터 진행 막기
        }

        if (refreshToken == null) { // RefreshToken 이 없거나 유효 하지 않으면 AccessToken 을 검사하고 인증하는 로직을 수행한다.
            checkAccessTokenAndAuthentication(request, response, filterChain);
            // AccessToken 이 없거나 유효하지 않으면 인증 객체가 담기지 않기 때문에 오류가 발생한다.
            // AccessToken 이 유효하다면 인증 객체가 담긴 상태로 다음 필터로 넘어가기 때문에 인증에 성공한다.
        }
    }

    // 리프레시 토큰으로 유저 정보 찾고 액세스 / 리프레시 토큰 재발급
    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        userRepository.findByRefreshToken(refreshToken) // 헤더에서 추출한 RefreshToken 으로  DB 에서 유저를 찾는다.
                .ifPresent(user -> { // 해당 유저가 존재한다면
                    String reIssuedRefreshToken = reIssueRefreshToken(user); // RefreshToken 을 재발급 하고 DB 에 저장시킨다.
                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(user.getUsername(), user.getNickname()),
                            // 응답 헤더에 새로 생성한 액세스 / 리프레스 토큰을 보낸다.
                            reIssuedRefreshToken);
                });
    }
    
    // 리프레시 토큰 재발급 후 DB에 리프레시 토큰 업데이트
    private String reIssueRefreshToken(User user) {
        String reIssuedRefreshToken = jwtService.createRefreshToken(); // 리프레시 토큰을 발급한다.
        user.updateRefreshToken(reIssuedRefreshToken); // DB 에 재발급된 리프레시 토큰을 업데이트 한다.
        userRepository.saveAndFlush(user); // 저장시킨다.
        return reIssuedRefreshToken;
        // 재발급된 리프레시 토큰을 반환한다.
    }

    // 액세스 토큰 체크 후 인증 처리 메소드
    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        jwtService.extractAccessToken(request) // 액세스 토큰을 추출한다.
                .filter(jwtService::isTokenValid) // 추출한 액세스 토큰이 유효하고
                .ifPresent(accessToken -> jwtService.extractUsername(accessToken) // 존재한다면 액세스 토큰에서 유저 네임을 추출한다.
                        .ifPresent(username -> userRepository.findByUsername(username) // 추출 값이 존재한다면 유저 객체를 찾는다.
                                .ifPresent(this::saveAuthentication))); // 추출 값이 존재한다면 인증 처리 한다.

        filterChain.doFilter(request, response);
        // 다음 필터로 넘긴다.
    }

    // 인증 허가
    public void saveAuthentication(User user) { // 우리가 생성한 회원 객체를 받는다.

        // 우리가 생성한 회원 객체에서 비밀번호를 추출한다.
        String password = user.getPassword();

        if (password == null) {
            // 소셜로그인의 경우 로그인시 password 값이 null 인데 인증 처리 시 비밀번호가 null 값이 들어가서는 안된다.
            password = RandomPasswordUtil.generateRandomPassword();
            // 임의의 패스워드를 만들어 비밀번호를 설정시켜준다.
        }

        // UserDetails 객체 생성
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(password)
                .roles(user.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
        // credentials 보통 비밀번호로 하지만 인증 시에는 보통 null 로 제거한다. (잘 모르겠음)
        // UserDetails 의 Collection<? extends GrantedAuthority> 에 Set<GrantedAuthority> 형식의 권한이 저장되어 있다.
        // authoritiesMapper 를 new 로 생성하여 mapAuthorities 안에 저장한다.

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // SecurityContextHolder.getContext()로 SecurityContext 를 꺼낸 후 위에서 만든 Authentication 객체에 대한 인증 허가 처리를 해준다.
    }

}
