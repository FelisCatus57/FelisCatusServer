package com.example.backend.domain.story.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "visitors")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoryVisitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitors")
    private Long id;




}
