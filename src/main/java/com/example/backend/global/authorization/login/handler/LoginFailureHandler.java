package com.example.backend.global.authorization.login.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

@Slf4j

// JWT 로그인 실패 시 처리
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 상태를 400 BadRequest 상태로 설정한다.
        response.setCharacterEncoding("UTF-8"); // 인코딩 형식을 설정한다.
        response.setContentType("text/plain;charset=UTF-8"); // ContentType 을 설정한다.
        response.getWriter().write("로그인 실패하였습니다. 아이디나 비밀번호를 확인해주세요.");
        log.info("로그인에 실패했습니다. 메시지 : {}", exception.getMessage());
    }
}
