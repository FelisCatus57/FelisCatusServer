package com.example.backend.global.authorization.login.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomJsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/login"; // "/login"으로 오는 요청을 처리
    private static final String HTTP_METHOD = "POST"; // 로그인 HTTP 메소드는 POST
    private static final String CONTENT_TYPE = "application/json"; // JSON 타입의 데이터로 오는 로그인 요청만 처리
    private static final String USERNAME_KEY = "username"; // 회원 로그인 시 아이디 요청 JSON Key : "username"
    private static final String PASSWORD_KEY = "password"; // 회원 로그인 시 비밀번호 요청 JSon Key : "password"
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD); // "/login" + POST로 온 요청에 매칭된다.

    private final ObjectMapper objectMapper;

    public CustomJsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
        // 부모클래스의 생성자 파라미터를 위의 설정된 경로로 값이 들어왔을때 실행할 수 있도록 설정
        this.objectMapper = objectMapper;
    }


    /*
    {
        "username" : "test@test.com"
        "password" : "test123"
    }
    */
    // 인증 처리
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        // ContentType 의 값이 null 이거나 ContentType 이 application/json 이 아니라면 AuthenticationServiceException 를 발생시킨다.
        if(request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)  ) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }

        /*
        간단한 문자열 처리를 위해서 문자열로 파일을 복사한다.
        stream 은 bytecode 이므로 String 으로 바꿀때는 어떤 인코딩으로 바꿀건지 설정해주어야 한다. 지정 안할경우 Default 값 이 설정된다.
        POST 메서드를 사용하면서 CONTENT-TYPE 이 "application/json" 형식일 때 발생하는데,
        이를 RequestBody post data 라고 하며 이러한 값은
        Request.getInputStream() 혹은 Request.getReader()를 통해 직접 읽어와야 한다고 한다.
        */
        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);


        //JSON 요청을 String 으로 변환한 messageBody 를 objectMapper.readValue()를 통해 Map 으로 변환하여 각각 Username, Password 로 저장한다. (파싱 과정)
        Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);

        String username = usernamePasswordMap.get(USERNAME_KEY);
        String password = usernamePasswordMap.get(PASSWORD_KEY);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // UsernamePasswordAuthenticationToken 객체는 AuthenticationManager 가 인증 시 사용할 인증 대상 객체가 된다.
        //principal(username) 과 credentials(password) 전달
        // 여기서 만든 UsernamePasswordAuthenticationToken 인증 대상 객체를 통해

        return this.getAuthenticationManager().authenticate(authRequest);
        //인증 처리 객체인 AuthenticationManager 가 인증 성공/인증 실패 처리를 하게 된다.
    }
}
