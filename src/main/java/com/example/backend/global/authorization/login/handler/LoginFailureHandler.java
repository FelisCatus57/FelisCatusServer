package com.example.backend.global.authorization.login.handler;

import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.result.ResultResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        log.info("로그인에 실패했습니다. 메시지 : {}", exception.getMessage());

        ResultResponseDTO resultResponseDTO = new ResultResponseDTO(ErrorCodeMessage.ACCOUNT_MISMATCH, "");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        new ObjectMapper().writeValue(response.getWriter(), resultResponseDTO);
    }
}
