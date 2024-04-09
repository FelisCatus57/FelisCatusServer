package com.example.backend.domain.user.service;

import com.example.backend.common.minio.MinioUploader;
import com.example.backend.domain.feed.dto.PostResponse;
import com.example.backend.domain.feed.service.PostService;
import com.example.backend.domain.follow.service.FollowService;
import com.example.backend.domain.user.Enum.Gender;
import com.example.backend.domain.user.Enum.Role;
import com.example.backend.domain.user.dto.MiniMenuUserResponse;
import com.example.backend.domain.user.dto.UserProfileEditRequest;
import com.example.backend.domain.user.dto.UserProfileResponse;
import com.example.backend.domain.user.dto.UserRegisterRequest;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.exception.NicknameAlreadyExistedException;
import com.example.backend.domain.user.exception.UserNotExistedException;
import com.example.backend.domain.user.exception.UsernameAlreadyExistedException;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.authorization.jwt.service.JwtService;
import com.example.backend.global.image.Image;
import com.example.backend.global.image.ImageType;
import com.example.backend.global.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {


    private static final String MINIO_PROFILE_DIR = "profile";

    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PostService postService;
    private final MinioUploader minioUploader;
    private final JwtService jwtService;
    private final FollowService followService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
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

    @Transactional
    public UserProfileResponse getProfile(String nickname) {

        User loginUser = authUtil.getLoginUser();

        List<PostResponse> myPost = postService.getAllPostByUserNickname(nickname);

        User findUser = userRepository.findByNickname(nickname).orElseThrow(
                () -> new UserNotExistedException());

        Long followerCount = followService.getFollowerCount(loginUser.getId());
        Long followingCount = followService.getFollowingCount(loginUser.getId());

        UserProfileResponse userProfileResponse = new UserProfileResponse(findUser, myPost, followerCount, followingCount);

        if (loginUser.getId().equals(findUser.getId())) {
            userProfileResponse.setMe(true);
        }

        userProfileResponse.setUserPostCount(postService.getUserPostCount(nickname));

        return userProfileResponse;

    }

//    @Transactional
//    public boolean checkPassword(String rawPassword) {
//        User loginUser = authUtil.getLoginUser();
//
//        if (!bCryptPasswordEncoder.matches(rawPassword, loginUser.getPassword())) {
//            new PasswordNotMatchException();
//            return false;
//        } else {
//            return true;
//        }
//    }

    @Transactional
    public void modifyProfile(UserProfileEditRequest userProfileEditRequest) {

        User loginUser = authUtil.getLoginUser();

        loginUser.updateIntroduce(userProfileEditRequest.getIntroduce().isEmpty() ? loginUser.getIntroduce() : userProfileEditRequest.getIntroduce());
        loginUser.updateWebsite(userProfileEditRequest.getWebsite().isEmpty() ? loginUser.getWebsite() : userProfileEditRequest.getWebsite());
        loginUser.updatePhoneNo(userProfileEditRequest.getPhoneNo().isEmpty() ? loginUser.getPhoneNo() : userProfileEditRequest.getPhoneNo());

        Gender gender = userProfileEditRequest.getGender();

        loginUser.updateGender(userProfileEditRequest.getGender() == null ? loginUser.getGender() : gender);

        userRepository.save(loginUser);

    }

    @Transactional
    public void updateImage(MultipartFile multipartFile) {

        User loginUser = authUtil.getLoginUser();

        deleteImage();

        Image image = minioUploader.to(multipartFile, MINIO_PROFILE_DIR);

        loginUser.updateImage(image);

        userRepository.save(loginUser);

    }

    @Transactional
    public void deleteImage() {

        User loginUser = authUtil.getLoginUser();

        if (!loginUser.getImage().getImageUUID().equals("base-UUID")) {
            minioUploader.deleteImage(loginUser.getImage(), MINIO_PROFILE_DIR);
        }

        loginUser.resetImage();
        userRepository.save(loginUser);
    }

    public MiniMenuUserResponse getMiniMenuUser() {

        User loginUser = authUtil.getLoginUser();

        return new MiniMenuUserResponse(loginUser);
    }

    @Transactional
    public void reIssueAccessToken(String nickname, String refreshToken) {

    }

}
