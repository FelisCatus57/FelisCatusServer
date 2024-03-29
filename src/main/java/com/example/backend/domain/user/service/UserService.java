package com.example.backend.domain.user.service;

import com.example.backend.domain.user.Enum.Gender;
import com.example.backend.domain.user.Enum.Role;
import com.example.backend.domain.user.dto.UserRegisterRequest;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.exception.NicknameAlreadyExistedException;
import com.example.backend.domain.user.exception.UsernameAlreadyExistedException;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.error.ErrorCodeMessage;
import com.example.backend.global.error.exception.EntityNotExistedException;
import com.example.backend.global.image.Image;
import com.example.backend.global.image.ImageType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(UserRegisterRequest userRegisterRequest) throws Exception{

        if (userRepository.existsUserByUsername(userRegisterRequest.getUsername())) {
            throw new UsernameAlreadyExistedException();
        }

        if (userRepository.existsUsersByNickname(userRegisterRequest.getNickname())) {
            throw new NicknameAlreadyExistedException();
        }

        User user = User.builder()
                .username(userRegisterRequest.getUsername())
                .password(userRegisterRequest.getPassword())
                .nickname(userRegisterRequest.getNickname())
                .name(userRegisterRequest.getName())
                .role(Role.USER)
                .gender(Gender.PRIVATE)
                .image(Image.builder()
                        .imageUrl("http://uncertain.shop:9000/sample/base-UUID.jpg")
                        .imageType(ImageType.JPG)
                        .imageName("base")
                        .imageUUID("base-UUID")
                        .build())
                .build();

        user.setEncPassword(bCryptPasswordEncoder);

        userRepository.save(user);
    }
}
