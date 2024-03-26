package com.example.backend.global.util;

import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.exception.UserNotExistedException;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.EntityNotExistedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final UserRepository userRepository;

    // 로그인한 사용자의 유저 번호를 가져옴
    public Long getLoginUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        User findUser = userRepository.findByUsername(((UserDetails) principal).getUsername()).orElseThrow(
                () -> new EntityNotExistedException(ErrorCodeMessage.USER_NOT_EXISTED)
        );

        return findUser.getId();

    }

    // 로그인한 사용자의 아이디를 가져옴
    public String getLoginUserUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        return userDetails.getUsername();
    }

    // 로그인한 사용자의 닉네임을 가져옴
    public String getLoginUserNickname() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        User findUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new EntityNotExistedException(ErrorCodeMessage.USER_NOT_EXISTED)
        );

        return findUser.getNickname();
    }

    // 로그인한 사용자의 객체를 가져옴
    public User getLoginUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new UserNotExistedException());

        return user;
    }

}
