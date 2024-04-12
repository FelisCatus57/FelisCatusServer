package com.example.backend.domain.story.repository;

import com.example.backend.domain.story.entity.StoryVisitor;
import com.example.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StoryVisitorRepository extends JpaRepository<StoryVisitor, Long> {
    List<StoryVisitor> findAllByAndId (List<StoryVisitor> visitors, User user);
}
