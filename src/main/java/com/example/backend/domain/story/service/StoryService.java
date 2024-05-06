package com.example.backend.domain.story.service;

import com.example.backend.common.minio.MinioUploader;
import com.example.backend.domain.follow.dto.FollowingResponse;
import com.example.backend.domain.follow.service.FollowService;
import com.example.backend.domain.story.dto.StoryUploadRequest;
import com.example.backend.domain.story.dto.StoryUploadResponse;
import com.example.backend.domain.story.dto.StoryView;
import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.story.exception.StoryCanNotDeleteException;
import com.example.backend.domain.story.exception.StoryNotExistException;
import com.example.backend.domain.story.repository.StoryRepository;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.exception.UserNotExistedException;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.image.Image;
import com.example.backend.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryService {

    private static final String MINIO_STORY_DIR = "story";

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final AuthUtil authUtil;
    private final FollowService followService;
    private final MinioUploader minioUploader;
    
    // 생성
    @Transactional
    public StoryUploadResponse storyUpload(StoryUploadRequest storyUploadRequest) {

        MultipartFile file = storyUploadRequest.getFile();

        Image image = minioUploader.to(file, MINIO_STORY_DIR);

        User loginUser = authUtil.getLoginUser();

        Story story = new Story(loginUser, image);

        Story save = storyRepository.save(story);

        return new StoryUploadResponse(save.getId());
    }

    // 삭제
    @Transactional
    public void deleteStory(Long storyId) {

        User loginUser = authUtil.getLoginUser();

        Story story = storyRepository.findById(storyId).orElseThrow(
                () -> new StoryNotExistException());

        if (!story.getUser().getId().equals(loginUser.getId())) {
            throw new StoryCanNotDeleteException();
        }

        minioUploader.deleteImage(story.getImage(), MINIO_STORY_DIR);

        storyRepository.delete(story);
    }

    // 가져오기 (팔로우 유저만)
    @Transactional
    public List<StoryView> storyView() {

        // 내가 반환 해야하는 팔로잉 목록유저들의 스토리
        List<StoryView> storyViews = new ArrayList<>();

        followingListStories().stream()
                .forEach( story -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
                    LocalDateTime parse = LocalDateTime.parse(story.getCreatedDate(), formatter);
                    LocalDateTime end = parse.plusDays(1);

                    if ( parse.isBefore(end)) {
                        storyViews.add(new StoryView(story));
                    }
                });
        return storyViews;
    }
    
    // 내가 팔로우한 유저들의 스토리 리스트 반환
    private List<Story> followingListStories() {

        List<Story> returnVal = new ArrayList<>();

        User loginUser = authUtil.getLoginUser();

        // 유저가 팔로잉한 팔로잉 리스트
        List<FollowingResponse> followingResponses = followService.followingList(loginUser.getNickname());

        followingResponses.stream()
                .forEach(following -> {
                    Long userId = following.getResponse().getUserId();
                    User findUser = userRepository.findById(userId).orElseThrow(
                            () -> new UserNotExistedException());

                    // 내가 팔로우한 유저들의 스토리
                    List<Story> stories = storyRepository.findStoriesByUser(findUser);

                    stories.forEach(s -> returnVal.add(s));
                });
        return returnVal;
    }

}
