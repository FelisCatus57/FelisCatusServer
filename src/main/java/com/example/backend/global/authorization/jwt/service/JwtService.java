package com.example.backend.global.authorization.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.exception.JwtTokenInvalidException;
import com.example.backend.domain.user.exception.UserNotExistedException;
import com.example.backend.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    // JWT 암호키
    @Value("${jwt.secretKey}")
    private String secretKey;

    // JWT Access Token 만료기간
    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    // JWT Refresh Token 만료기간
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    // JWT Access Token Header
    @Value("${jwt.access.header}")
    private String accessHeader;

    // JWT Refresh Token Header
    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String USERNAME_CLAIM = "username";
    private static final String NICKNAME_CLAIM = "nickname";
    private static final String BEARER = "Bearer ";

    private final UserRepository userRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public String createAccessToken(String username, String nickname) {
        Date now = new Date();
        return JWT.create() // JWT 토큰을 생성하는 빌더 반환
                .withSubject(ACCESS_TOKEN_SUBJECT) // JWT Subject 지정 -> AccessToken
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod)) // 토큰 만료 시간 설정 (현재시간 + 만료기간)

                //클레임으로는 username 과 nickname 사용합니다.
                //추가할 경우 .withClaim(클래임 이름, 클래임 값) 으로 설정하면 된다.
                .withClaim(USERNAME_CLAIM, username)
                .withClaim(NICKNAME_CLAIM, nickname)
                .sign(Algorithm.HMAC512(secretKey)); // HMAC512 알고리즘 사용, application.yml 에서 지정한 secret 키로 암호화
    }

    // RefreshToken 생성
    // RefreshToken 은 Claim 에 username 넣지 않으므로 withClaim() X
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    // AccessToken 헤더에 실어서 보내기
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessHeader, accessToken);
        log.info("재발급된 Access Token : {}", BEARER + accessToken);
    }

    // AccessToken + RefreshToken 헤더에 실어서 보내기
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    // 헤더에서 AccessToken 추출
    // 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    // 헤더에서 RefreshToken 파싱
    // 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    // AccessToken 에서 username 추출
    public Optional<String> extractUsername(String accessToken) {
        try {
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey)) // JWT.require()로 검증기 생성
                    .build() // 반환된 빌더로 JWT verifier 생성
                    .verify(accessToken)
                    // verify 로 AccessToken 검증
                    // accessToken 을 검증 후 유효하지 않다면 예외 발생
                    .getClaim(USERNAME_CLAIM)
                    // claim(Username) 가져오기 (추출)
                    .asString());
        } catch (JwtTokenInvalidException e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
            // 유효하지 않다면 빈 Optional 객체 반환
        }
    }

    // AccessToken 에서 nickname 추출
    public Optional<String> extractNickname(String accessToken) {
        try {
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey)) // JWT.require()로 검증기 생성
                    .build() // 반환된 빌더로 JWT verifier 생성
                    .verify(accessToken)
                    // verify 로 AccessToken 검증
                    // accessToken 을 검증 후 유효하지 않다면 예외 발생
                    .getClaim(NICKNAME_CLAIM)
                    // claim(Nickname) 가져오기 (추출)
                    .asString());
        } catch (JwtTokenInvalidException e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
            // 유효하지 않다면 빈 Optional 객체 반환
        }
    }

    // AccessToken 헤더 설정
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader,  BEARER + accessToken);
    }

    // RefreshToken 헤더 설정
    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, BEARER + refreshToken);
    }

    // RefreshToken DB 저장(업데이트)
    public void updateRefreshToken(String username, String refreshToken) {
        userRepository.findByUsername(username)
                .ifPresentOrElse(
                        user -> user.updateRefreshToken(refreshToken), // 존재
                        () -> new UserNotExistedException() // 존재 X
                );
    }

    // 토큰 유효성 검증
    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token); // JWT.require()로 검증기 생성 후 verify 로 token 검증
            return true;
            // 유효하면 true 반환
        } catch (JwtTokenInvalidException e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
            // 유효하지 않으면 로그와 함께 false 반환
        }
    }

    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        userRepository.findByRefreshToken(refreshToken) // 헤더에서 추출한 RefreshToken 으로  DB 에서 유저를 찾는다.
                .ifPresent(user -> { // 해당 유저가 존재한다면
                    String reIssuedRefreshToken = reIssueRefreshToken(user); // RefreshToken 을 재발급 하고 DB 에 저장시킨다.
                    sendAccessAndRefreshToken(response, createAccessToken(user.getUsername(), user.getNickname()),
                            // 응답 헤더에 새로 생성한 액세스 / 리프레스 토큰을 보낸다.
                            reIssuedRefreshToken);
                });
    }

    // 리프레시 토큰 재발급 후 DB에 리프레시 토큰 업데이트
    private String reIssueRefreshToken(User user) {
        String reIssuedRefreshToken = createRefreshToken(); // 리프레시 토큰을 발급한다.
        user.updateRefreshToken(reIssuedRefreshToken); // DB 에 재발급된 리프레시 토큰을 업데이트 한다.
        userRepository.saveAndFlush(user); // 저장시킨다.
        return reIssuedRefreshToken;
        // 재발급된 리프레시 토큰을 반환한다.
    }
}

