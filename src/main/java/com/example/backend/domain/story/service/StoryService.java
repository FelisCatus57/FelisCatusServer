package com.example.backend.domain.story.service;

import com.example.backend.common.minio.MinioUploader;
import com.example.backend.domain.follow.dto.FollowingResponse;
import com.example.backend.domain.follow.service.FollowService;
import com.example.backend.domain.story.dto.StoryMenuResponse;
import com.example.backend.domain.story.dto.StoryUploadRequest;
import com.example.backend.domain.story.dto.StoryUploadResponse;
import com.example.backend.domain.story.dto.StoryView;
import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.story.exception.StoryCanNotDeleteException;
import com.example.backend.domain.story.exception.StoryEmptyException;
import com.example.backend.domain.story.exception.StoryNotExistException;
import com.example.backend.domain.story.exception.UserStoryNotExistedException;
import com.example.backend.domain.story.repository.StoryRepository;
import com.example.backend.domain.story.repository.StoryViewUserRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StoryService {

    private static final String MINIO_STORY_DIR = "story";

    private final StoryViewUserService storyViewUserService;
    private final StoryViewUserRepository storyViewUserRepository;
    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final FollowService followService;
    private final MinioUploader minioUploader;
    private final AuthUtil authUtil;

    // 생성
    @Transactional
    public StoryUploadResponse storyUpload(StoryUploadRequest storyUploadRequest) {

        User loginUser = authUtil.getLoginUser();

        List<Long> ids = new ArrayList<>();

        List<MultipartFile> files = storyUploadRequest.getFiles();

        files.forEach(
                file -> {
                    Image uploadImage = minioUploader.to(file, MINIO_STORY_DIR);
                    Story uploadStory = new Story(loginUser, uploadImage);
                    Story save = storyRepository.save(uploadStory);
                    ids.add(save.getId());
                });

        return new StoryUploadResponse(ids);
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

    // 내가 팔로우 한 유저들 중에서 스토리를 올린 유저들만 조회 (중복 제거를 위해 Set)
    @Transactional
    public Set<StoryMenuResponse> storyViewList() {

        List<Story> stories = followingListStories();

        Set<StoryMenuResponse> userSet = new HashSet<>();

        stories.forEach(
                story -> {
                    userSet.add(new StoryMenuResponse(story));
                });

        return userSet;
    }

    @Transactional
    public List<StoryView> storyView(Long userId) {

        User loginUser = authUtil.getLoginUser();

        List<StoryView> views = new ArrayList<>();

        User findUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotExistedException());

        List<Story> userStories = storyRepository.findStoriesByUser(findUser).orElseThrow(
                () -> new UserStoryNotExistedException());


        userStories.forEach(
                story -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
                    LocalDateTime parse = LocalDateTime.parse(story.getCreatedDate(), formatter);
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime end = parse.plusDays(1);

                    if ( now.isAfter(parse) && now.isBefore(end)) {

                        if (!storyViewUserRepository.existsByStoryAndUser(story, loginUser)) {
                            storyViewUserService.addViewUser(story, loginUser);
                        }
                        views.add(new StoryView(story));
                    }
                });


        if (views.size() == 0) {
            throw new UserStoryNotExistedException();
        } else {
            return views;
        }
    }


//    @Transactional
//    public List<StoryView> storyView(Long userId) {
//
//        List<StoryView> views = new ArrayList<>();
//
//        User findUser = userRepository.findById(userId).orElseThrow(
//                () -> new UserNotExistedException());
//
//        List<Story> stories = followingListStories();
//
//        stories.stream().forEach(
//                story -> {
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
//                    LocalDateTime parse = LocalDateTime.parse(story.getCreatedDate(), formatter);
//                    LocalDateTime now = LocalDateTime.now();
//                    LocalDateTime end = parse.plusDays(1);
//                    if (story.getUser().getId().equals(findUser.getId()) && (now.isAfter(parse) && now.isBefore(end))) {
//                        views.add(new StoryView(story));
//                    }
//                });
//
//        return value;
//    }

    //TODO 목록 조회 랑 실제 정보 가져오는 데이터 비슷 (리팩 필요)
    // 조회 가져오기 (팔로우 유저만)
    // 유저 목록 별로 보여줘야 하니까 유저 목록을 리스트로 만들어서 각각의 번호를 반환해줘야 할듯

//    @Transactional
//    public List<StoryIdResponse> storyViewList() {
//
//        // 내가 반환 해야하는 팔로잉 목록 유저들의 스토리 목록
//        List<StoryIdResponse> storyIdResponses = new ArrayList<>();
//
//        User loginUser = authUtil.getLoginUser();
//
//        Set<User> userSet = new HashSet<>();
//
//
//
//        followingListStories().stream()
//                .forEach( story -> {
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss");
//                    LocalDateTime parse = LocalDateTime.parse(story.getCreatedDate(), formatter);
//                    LocalDateTime now = LocalDateTime.now();
//                    LocalDateTime end = parse.plusDays(1);
//
//                    if ( now.isAfter(parse) && now.isBefore(end)) {
//                        storyViewUserService.addViewUser(story, loginUser);
//                        storyIdResponses.add(new StoryIdResponse(story));
//                    }
//                });
//        return storyIdResponses;
//    }

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
                    List<Story> stories = storyRepository.findStoriesByUser(findUser).orElseThrow(
                            () -> new StoryEmptyException());

                    stories.forEach(s -> returnVal.add(s));
                });
        return returnVal;
    }

}
