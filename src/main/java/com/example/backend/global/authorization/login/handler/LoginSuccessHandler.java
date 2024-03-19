package com.example.backend.global.authorization.login.handler;

import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.authorization.jwt.service.JwtService;
import com.example.backend.global.result.ResultResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
// JWT 로그인 성공 시 처리
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    
    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration; // AccessToken 만료 시간
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                     Authentication authentication) {
        String username = extractUsername(authentication); // 인증 정보에서 Username 추출

        String nickname = extractNickname(username); // Username 을 사용하여 Nickname 추출

        // 닉네임 가져오는게 약간 변수가 생겼네...(이렇게 하면 안될것 같은데;)
        String accessToken = jwtService.createAccessToken(username, nickname); // createAccessToken 을 사용하여 AccessToken 발급
        String refreshToken = jwtService.createRefreshToken(); // createRefreshToken 을 사용하여 RefreshToken 발급

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken); // 응답 헤더에 액세스 / 리프레시 토큰 실어서 전달

        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    user.updateRefreshToken(refreshToken);
                    userRepository.saveAndFlush(user);
                });
        
        // 발급한 RefreshToken 을 저장한다.
        log.info("로그인에 성공하였습니다. 아이디 : {}", username);
        log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
        log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
    }

    // 인증 객체에서 Username 을 파싱한다.
    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    // 파싱한 Username 을 가지고 유저 객체에서 Nickname 을 파싱한다.
    private String extractNickname(String username) {
        User nickUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        return nickUser.getNickname();
    }

}
