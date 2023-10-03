package com.twitter.apis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twitter.apis.model.RetweetEntity;

public interface RetweetRepository extends JpaRepository<RetweetEntity, Long>{

    boolean existsByTweetIdAndUserId(Long tweetId, Long userId);
    
}
