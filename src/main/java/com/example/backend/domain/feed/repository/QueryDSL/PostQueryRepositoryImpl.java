package com.example.backend.domain.feed.repository.QueryDSL;

import com.example.backend.domain.feed.entity.Post;
import com.example.backend.domain.feed.entity.QPost;
import com.example.backend.domain.follow.entity.QFollow;
import com.example.backend.domain.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> findAllByFollowingPost(String nickname) {

        QUser user = QUser.user;
        QPost post = QPost.post;
        QFollow follow = QFollow.follow;

        List<Post> fetch = jpaQueryFactory.select(post)
                .from(post)
                .innerJoin(follow).on(post.user.id.eq(follow.following.id))
                .innerJoin(user).on(user.id.eq(follow.follower.id))
                .where(user.nickname.eq(nickname))
//                .offset()
//                .limit()
                .fetch();


        Long cnt = jpaQueryFactory.select(post.count())
                .from(post)
                .innerJoin(follow).on(post.user.id.eq(follow.following.id))
                .innerJoin(user).on(user.id.eq(follow.follower.id))
                .where(user.nickname.eq(nickname))
                .fetchOne();

        return fetch;
    }

    @Override
    public List<Post> findAllByNotFollowingPost(String nickname) {

        QUser user = QUser.user;
        QPost post = QPost.post;
        QFollow follow = QFollow.follow;

        List<Post> fetch = jpaQueryFactory.select(post)
                .from(post)
                .innerJoin(follow).on(post.user.id.eq(follow.following.id))
                .innerJoin(user).on(user.id.eq(follow.follower.id))
                .where(user.nickname.ne(nickname).and(post.user.nickname.ne(nickname)))
//                .offset()
//                .limit()
                .fetch();


        return fetch;
    }


}
