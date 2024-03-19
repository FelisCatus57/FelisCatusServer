package com.example.backend.global.config;

import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.authorization.jwt.filter.JwtAuthenticationProcessingFilter;
import com.example.backend.global.authorization.jwt.service.JwtService;
import com.example.backend.global.authorization.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import com.example.backend.global.authorization.login.handler.LoginFailureHandler;
import com.example.backend.global.authorization.login.handler.LoginSuccessHandler;
import com.example.backend.global.authorization.login.service.CustomLoginService;
import com.example.backend.global.authorization.oauth2.handler.OAuth2LoginFailureHandler;
import com.example.backend.global.authorization.oauth2.handler.OAuth2LoginSuccessHandler;
import com.example.backend.global.authorization.oauth2.service.CustomOAuth2UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CustomLoginService customLoginService;
    private final ObjectMapper objectMapper;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setUserDetailsService(customLoginService);
        return new ProviderManager(provider);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, userRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler());

        return customJsonUsernamePasswordAuthenticationFilter;

    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationFilter = new JwtAuthenticationProcessingFilter(jwtService, userRepository);
        return jwtAuthenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors( (cors) -> cors
                        .configurationSource(new CorsConfigurationSource() {

                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                                CorsConfiguration config = new CorsConfiguration();

                                config.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000"));
                                config.setAllowedMethods(Arrays.asList("*"));
                                config.setAllowCredentials(true);
                                config.setAllowedHeaders(Arrays.asList("*"));
                                config.setExposedHeaders(Arrays.asList("Authorization", "Authorization-refresh"));
                                config.setMaxAge(3600L);


                                return config;
                            }
                        }));

        http
                .csrf( (csrf) -> csrf.disable());
        http
                .formLogin( (login) -> login.disable());
        http
                .httpBasic( (basic) -> basic.disable());
        http
                .sessionManagement( (session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .authorizeHttpRequests( (auth) -> auth
                        .requestMatchers("/", "/sign-up", "/test1" , "/postup2").permitAll()
                        .anyRequest().authenticated());

        http
                .oauth2Login( (oauth2) -> oauth2
                        .successHandler(oAuth2LoginSuccessHandler) // 동의하고 계속하기를 눌렀을 때
                        .failureHandler(oAuth2LoginFailureHandler) // 소셜 로그인 실패 시
                        .userInfoEndpoint( (userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))));

        http
                .addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http
                .addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
