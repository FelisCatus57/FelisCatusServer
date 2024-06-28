package com.example.backend.domain.feed.entity;

import com.example.backend.global.BaseTimeEntity;
import com.example.backend.global.image.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@Table(name = "post_images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "imageUrl", column = @Column(name = "user_img_url")),
            @AttributeOverride(name = "imageType", column = @Column(name = "user_img_type")),
            @AttributeOverride(name = "imageName", column = @Column(name = "user_img_name")),
            @AttributeOverride(name = "imageUUID", column = @Column(name = "user_img_uuid"))
    })
    private Image image;

    @Builder
    public PostImage(Post post, Image image) {
        this.post = post;
        this.image = image;
    }
}
