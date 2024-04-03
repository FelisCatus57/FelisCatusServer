package com.example.backend.domain.story.repository;

import com.example.backend.domain.story.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//CURD 구현
public interface StoryRepository extends JpaRepository<Story, Long> {



}
