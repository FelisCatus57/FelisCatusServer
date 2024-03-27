package com.example.backend.domain.user.repository;

import com.example.backend.domain.user.Enum.SocialType;
import com.example.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByRefreshToken(String refreshToken);

    boolean existsUserByUsername(String username);

    boolean existsUserByNickname(String nickname);


    /*
    소셜 타입과 소셜의 식별값으로 회원을 찾는 메소드
    정보 제공을 동의하면 DB에 저장해야하지만, 아직 추가정보(기타 정보)를 입력 받지 않았으므로
    유저 객체는 DB에 존재하지만 기타 정보가 저장되지 않은 상태이다.
    따라서 기타 정보를 입력받아 회원 가입을 진행할 때 소셜 타입, 식별자로 해당 회원을 찾기 위한 메소드
    */
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
