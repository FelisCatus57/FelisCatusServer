package com.example.backend.domain.story.entity;

import com.example.backend.domain.user.entity.User;
import com.example.backend.global.BaseTimeEntity;
import com.example.backend.global.image.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "stories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Story extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "imageUrl", column = @Column(name = "user_img_url")),
            @AttributeOverride(name = "imageType", column = @Column(name = "user_img_type")),
            @AttributeOverride(name = "imageName", column = @Column(name = "user_img_name")),
            @AttributeOverride(name = "imageUUID", column = @Column(name = "user_img_uuid"))
    })
    private Image image;

    @Builder
    public Story(User user, Image image) {
        this.user = user;
        this.image = image;
    }
    
}
