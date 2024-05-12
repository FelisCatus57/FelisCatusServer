package com.example.backend.domain.story.repository;

import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.story.entity.StoryViewUser;
import com.example.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryViewUserRepository extends JpaRepository<StoryViewUser, Long> {

    // TODO 조회 Repository 수정
    
    List<StoryViewUser> findAllByStory(Story story);

    List<StoryViewUser> findAllByUser(User user);

    List<StoryViewUser> findAllByStoryAndUser(Story story, User user);

    Boolean existsByStoryAndUser(Story story, User user);
}
