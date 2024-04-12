package com.example.backend.domain.story.entity;


import com.example.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "visitors")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoryVisitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitor_id")
    private Long id;


    @Column(name = "visitor_name")
    private String name;


    @Builder
    public StoryVisitor(Long id, String name){
        this.id = id;
        this.name = name;
    }

}
