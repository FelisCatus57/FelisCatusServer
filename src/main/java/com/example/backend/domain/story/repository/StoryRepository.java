package com.example.backend.domain.story.repository;

import com.example.backend.domain.story.entity.Story;
import com.example.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long> {

    Optional<Story> findById(Long id);

    List<Story> findStoriesByUser(User user);

//    List<Story> findStoriesByCreatedDateIsBetween(LocalDateTime from, LocalDateTime to);

}
