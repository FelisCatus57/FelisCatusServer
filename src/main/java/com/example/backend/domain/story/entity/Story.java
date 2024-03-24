package com.example.backend.domain.story.entity;
//story의 정보를 담는다

import com.example.backend.domain.user.entity.User;
import com.example.backend.global.image.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "stories")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_id")
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "image_id", column = @Column(name = "image_id")),
            @AttributeOverride(name = "image_url", column = @Column(name = "image_url"))

    })
    private Image image;

    @Column(name = "upload_at")
    @CreatedDate
    private LocalDateTime uploadAt;

    public Story(User user, Image image) {
        this.user = user;
        this.image = image;
    }
}
