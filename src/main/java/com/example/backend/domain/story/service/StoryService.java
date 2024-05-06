package com.example.backend.domain.story.service;

import com.example.backend.common.minio.MinioUploader;
import com.example.backend.domain.follow.service.FollowService;
import com.example.backend.domain.story.dto.StoryUploadRequest;
import com.example.backend.domain.story.dto.StoryUploadResponse;
import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.story.exception.StoryCanNotDeleteException;
import com.example.backend.domain.story.exception.StoryNotExistException;
import com.example.backend.domain.story.repository.StoryRepository;
import com.example.backend.domain.user.entity.User;
import com.example.backend.global.image.Image;
import com.example.backend.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StoryService {

    private static final String MINIO_STORY_DIR = "story";

    private final StoryRepository storyRepository;
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

}
