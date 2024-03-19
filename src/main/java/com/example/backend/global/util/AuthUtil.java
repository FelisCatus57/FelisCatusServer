package com.example.backend.global.util;

import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.EntityNotExistedException;
import lombok.RequiredArgsConstructor;
import org.hibernate.metamodel.internal.MemberResolver;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final UserRepository userRepository;

    public Long getLoginUserIdOrNull() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        User findUser = userRepository.findByUsername(((UserDetails) principal).getUsername()).orElseThrow(
                () -> new EntityNotExistedException(ErrorCodeMessage.USER_NOT_EXISTED)
        );

        return findUser.getId();

    }

    public String getLoginUserUsernameOrNull() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        return userDetails.getUsername();
    }

    public String getLoginUserNicknameOrNull() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        User findUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new EntityNotExistedException(ErrorCodeMessage.USER_NOT_EXISTED)
        );

        return findUser.getNickname();
    }

    public User getLoginUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new EntityNotExistedException(ErrorCodeMessage.USER_NOT_EXISTED)
        );

        return user;
    }

}
