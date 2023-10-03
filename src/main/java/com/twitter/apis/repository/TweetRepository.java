package com.twitter.apis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.twitter.apis.DTO.TweetDTO;
import com.twitter.apis.model.TweetEntity;
import com.twitter.apis.model.UserEntity;

public interface TweetRepository extends JpaRepository<TweetEntity, Long> {

        List<TweetEntity> findByUser_Id(Long id);

        Optional<TweetEntity> findByTextAndUser(String text, UserEntity user);

        @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
        Optional<UserEntity> findByUniqueUsername(@Param("username") String username);

        @Query("SELECT new com.twitter.apis.DTO.TweetDTO(t.user.username, t.text, t.timestamp, t.likes, t.replies, t.retweets) "
                        +
                        "FROM TweetEntity t " +
                        "ORDER BY t.timestamp DESC")
        List<TweetDTO> getAllTweetsWithUserDetails();

        @Query("SELECT new com.twitter.apis.DTO.TweetDTO(t.user.username, t.text, t.timestamp, t.likes, t.replies, t.retweets) "
                        +
                        "FROM TweetEntity t WHERE t.user.id = :userId")
        List<TweetDTO> getAllTweetsByUserIdList(@Param("userId") Long userId);

        @Query("SELECT new com.twitter.apis.DTO.TweetDTO(t.user.username, t.text, t.timestamp, t.likes, t.replies, t.retweets) "
                        +
                        "FROM TweetEntity t")
        List<TweetDTO> findAllByOrderByTimestampDesc(Pageable pageable);

}
