package com.example.backend.domain.follow.service;

import com.example.backend.domain.follow.dto.FollowResponse;
import com.example.backend.domain.follow.dto.FollowUserMiniResponse;
import com.example.backend.domain.follow.dto.FollowerResponse;
import com.example.backend.domain.follow.dto.FollowingResponse;
import com.example.backend.domain.follow.entity.Follow;
import com.example.backend.domain.follow.exception.FollowCantMySelfException;
import com.example.backend.domain.follow.exception.UnFollowCantMySelfException;
import com.example.backend.domain.follow.repository.FollowRepository;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.exception.UserNotExistedException;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final AuthUtil authUtil;
    private final UserRepository UserRepository;
    private final FollowRepository followRepository;

    @Transactional
    public FollowResponse follow(String nickname) {

        Long loginUserId = authUtil.getLoginUserId();

        User follower = UserRepository.findById(loginUserId).orElseThrow(
                () -> new UserNotExistedException());

        User following = UserRepository.findByNickname(nickname).orElseThrow(
                () -> new UserNotExistedException());

        if (loginUserId.equals(following.getId())) {
            throw new FollowCantMySelfException();
        }

        Follow sFollow = new Follow(follower, following);

        Follow save = followRepository.save(sFollow);

        return new FollowResponse(save);
    }

    @Transactional
    public void unfollow(String nickname) {

        Long loginUserId = authUtil.getLoginUserId();

        User follower = UserRepository.findById(loginUserId).orElseThrow(
                () -> new UserNotExistedException());

        User following = UserRepository.findByNickname(nickname).orElseThrow(
                () -> new UserNotExistedException());

        if (loginUserId.equals(following.getId())) {
            throw new UnFollowCantMySelfException();
        }

        followRepository.deleteByFollowerIdAndFollowingId(follower.getId(), following.getId());
    }

    @Transactional
    public List<FollowerResponse> followerList(String nickname) {

        User findUser = UserRepository.findByNickname(nickname).orElseThrow(
                () -> new UserNotExistedException());

        List<Follow> followers = followRepository.findAllByFollowingId(findUser.getId());

        List<FollowerResponse> res = new ArrayList<>();

        followers.forEach(
                follow -> res.add(new FollowerResponse(follow.getId(), new FollowUserMiniResponse(follow.getFollower())))
        );

        return res;
    }

    @Transactional
    public List<FollowingResponse> followingList(String nickname) {

        User findUser = UserRepository.findByNickname(nickname).orElseThrow(
                () -> new UserNotExistedException());

        List<Follow> followings = findUser.getFollowings();

        List<FollowingResponse> res = new ArrayList<>();

        followings.forEach(
                follow -> res.add(new FollowingResponse(follow.getId(), new FollowUserMiniResponse(follow.getFollowing())))
        );

        return res;
    }

    public Long getFollowerCount(Long followerId) {
        return followRepository.countAllByFollowingId(followerId);
    }

    public Long getFollowingCount(Long followingId) {
        return followRepository.countAllByFollowerId(followingId);
    }
}
