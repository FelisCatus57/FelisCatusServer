package com.example.backend.domain.user.entity;

import com.example.backend.domain.follow.entity.Follow;
import com.example.backend.domain.user.Enum.Gender;
import com.example.backend.domain.user.Enum.Role;
import com.example.backend.domain.user.Enum.SocialType;
import com.example.backend.global.BaseTimeEntity;
import com.example.backend.global.image.Image;
import com.example.backend.global.image.ImageType;
import com.example.backend.global.util.ImageUtil;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String username; // 아이디 (UNIQUE)

    @Column(nullable = false, length = 200)
    private String password; // 비밀번호

    @Column(nullable = false, length = 10)
    private String name; // 이름

    @Column(nullable = false, unique = true, length = 12)
    private String nickname; // 닉네임 (UNIQUE)

    @Column(nullable = false)
    private String email; // 이메일

    @Enumerated(EnumType.STRING)
    private Role role; // 권한 (자동 ROLE_USER)

    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별 (처음에는 자동으로 PRIVATE)

    @Column(length = 11)
    private String phoneNo; // 전화번호

    @Column(length = 500)
    private String introduce; // 자기소개

    @Column(length = 50)
    private String website; // 웹사이트

    // TODO mappedBy 채우기
    @OneToMany(mappedBy = "follower")
    private List<Follow> followings = new ArrayList<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "imageUrl", column = @Column(name = "user_img_url")),
            @AttributeOverride(name = "imageType", column = @Column(name = "user_img_type")),
            @AttributeOverride(name = "imageName", column = @Column(name = "user_img_name")),
            @AttributeOverride(name = "imageUUID", column = @Column(name = "user_img_uuid"))
    })
    private Image image; // 프로필 이미지

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // 소셜로그인 시 플랫폼

    private String socialId; // 소셜로그인 시 저장될 ID

    private String refreshToken; // JWT 리프레시 토큰 저장

    @Builder
    public User(String username, String password, String name, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.role = Role.USER;
        this.gender = Gender.PRIVATE;
        this.image = Image.builder()
                .imageUrl("http://uncertain.shop:9000/sample/base-UUID.jpg")
                .imageType(ImageType.JPG)
                .imageName("base")
                .imageUUID("base-UUID")
                .build();
    }

    // 비밀번호 암호화
    public void setEncPassword(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.password = bCryptPasswordEncoder.encode(this.password);
    }

    // 프로필 이미지 삭제 -> 삭제 후 기본이미지로 설정 (if 기본이미지 ? 그냥 return)

    public void updateName(String name) {
        this.name = name;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void updateIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void updateWebsite(String website) {
        this.website = website;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

    public void updateImage(Image image) {
        resetImage();
        this.image = image;
    }

    public void resetImage() {
        if (this.image.getImageUUID().equals("base-UUID")) {
            return;
        }

        this.image = ImageUtil.getBaseImage();
    }

    // 리프레시 토큰 업데이트 메소드
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

}
