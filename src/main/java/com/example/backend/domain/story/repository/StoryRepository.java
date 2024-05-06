package com.example.backend.domain.story.repository;

import com.example.backend.domain.story.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long> {

    Optional<Story> findById(Long id);
}
