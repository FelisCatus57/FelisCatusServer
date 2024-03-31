package com.example.backend.domain.user.service;

import com.example.backend.domain.feed.dto.PostResponse;
import com.example.backend.domain.feed.service.PostService;
import com.example.backend.domain.user.Enum.Gender;
import com.example.backend.domain.user.Enum.Role;
import com.example.backend.domain.user.dto.UserProfileResponse;
import com.example.backend.domain.user.dto.UserRegisterRequest;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.exception.NicknameAlreadyExistedException;
import com.example.backend.domain.user.exception.UserNotExistedException;
import com.example.backend.domain.user.exception.UsernameAlreadyExistedException;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.image.Image;
import com.example.backend.global.image.ImageType;
import com.example.backend.global.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PostService postService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User signUp(UserRegisterRequest userRegisterRequest) {

        if (userRepository.existsUserByUsername(userRegisterRequest.getUsername())) {
            throw new UsernameAlreadyExistedException();
        }

        if (userRepository.existsUserByNickname(userRegisterRequest.getNickname())) {
            throw new NicknameAlreadyExistedException();
        }

        User user = User.builder()
                .username(userRegisterRequest.getUsername())
                .password(userRegisterRequest.getPassword())
                .nickname(userRegisterRequest.getNickname())
                .name(userRegisterRequest.getName())
                .email(userRegisterRequest.getEmail())
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

        User save = userRepository.save(user);

        return save;
    }

    public UserProfileResponse getProfile(String nickname) {

        User loginUser = authUtil.getLoginUser();

        List<PostResponse> myPost = postService.getAllPostByUserNickname(nickname);

        User findUser = userRepository.findByNickname(nickname).orElseThrow(
                () -> new UserNotExistedException());

        UserProfileResponse userProfileResponse = new UserProfileResponse(findUser, myPost);

        if (loginUser.getId().equals(findUser.getId())) {
            userProfileResponse.setMe(true);
        }

        userProfileResponse.setUserPostCount(postService.getUserPostCount(nickname));

        return userProfileResponse;

    }
}
