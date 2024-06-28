package com.example.backend.domain.story.entity;

import com.example.backend.domain.user.entity.User;
import com.example.backend.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "view_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoryViewUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "view_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id")
    private Story story;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public StoryViewUser(Story story, User user) {
        this.story = story;
        this.user = user;
    }

}
