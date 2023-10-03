package com.twitter.apis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twitter.apis.model.LikeEntity;
import com.twitter.apis.model.TweetEntity;
import com.twitter.apis.model.UserEntity;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    Optional<LikeEntity> findByUserAndTweet(UserEntity user, TweetEntity tweet);

    long countByTweetId(Long tweetId);

}
