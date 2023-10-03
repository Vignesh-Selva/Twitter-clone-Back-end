package com.twitter.apis.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.twitter.apis.DTO.TweetDTO;
import com.twitter.apis.model.CommentEntity;
import com.twitter.apis.model.LikeEntity;
import com.twitter.apis.model.RetweetEntity;
import com.twitter.apis.model.TweetEntity;
import com.twitter.apis.model.UserEntity;
import com.twitter.apis.repository.LikeRepository;
import com.twitter.apis.repository.RetweetRepository;
import com.twitter.apis.repository.TweetRepository;
import com.twitter.apis.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class TweetsService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final RetweetRepository retweetRepository;

    @Autowired
    public TweetsService(TweetRepository tweetRepository, UserRepository userRepository,
            LikeRepository likeRepository, RetweetRepository retweetRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.retweetRepository = retweetRepository;
    }

    // Create a tweet
    public ResponseEntity<?> createTweet(TweetDTO tweetDTO, Long userId) {

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (tweetDTO.getText().length() > 100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tweet length must be 100 characters or less");
        }

        Optional<TweetEntity> tweetEntityOptional = tweetRepository.findByTextAndUser(tweetDTO.getText(),
                userEntityOptional.get());
        if (tweetEntityOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tweet already exists");
        } else {
            if (userEntityOptional.isPresent()) {
                UserEntity userEntity = userEntityOptional.get();

                TweetEntity tweetEntity = new TweetEntity();
                tweetEntity.setText(tweetDTO.getText());
                tweetEntity.setTimestamp(new Date());

                tweetEntity.setUser(userEntity);
                tweetEntity = tweetRepository.save(tweetEntity);

                userEntity.setNumberOfTweets(userEntity.getNumberOfTweets() + 1);
                userRepository.save(userEntity);

                return ResponseEntity.status(HttpStatus.CREATED).body("Tweet created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
    }

    public boolean likeTweet(Long tweetId, Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        Optional<TweetEntity> tweetOptional = tweetRepository.findById(tweetId);

        if (userOptional.isPresent() && tweetOptional.isPresent()) {
            UserEntity user = userOptional.get();
            TweetEntity tweet = tweetOptional.get();

            Optional<LikeEntity> existingLike = likeRepository.findByUserAndTweet(user, tweet);
            if (existingLike.isPresent()) {
                likeRepository.delete(existingLike.get());
                tweet.setLikes(tweet.getLikes() - 1);
            } else {
                LikeEntity like = new LikeEntity(user, tweet);
                likeRepository.save(like);
                tweet.setLikes(tweet.getLikes() + 1);
            }
            tweetRepository.save(tweet);

            return true;
        } else {
            return false;
        }
    }

    public boolean isTweetLikedByUser(Long tweetId, Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        Optional<TweetEntity> tweetOptional = tweetRepository.findById(tweetId);

        if (userOptional.isPresent() && tweetOptional.isPresent()) {
            UserEntity user = userOptional.get();
            TweetEntity tweet = tweetOptional.get();

            Optional<LikeEntity> existingLike = likeRepository.findByUserAndTweet(user, tweet);
            return existingLike.isPresent();
        }
        return false;
    }

    public boolean commentOnTweet(Long tweetId, Long userId, @Valid TweetDTO tweetDTO) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        Optional<TweetEntity> tweetOptional = tweetRepository.findById(tweetId);

        if (userOptional.isPresent() && tweetOptional.isPresent()) {
            UserEntity user = userOptional.get();
            TweetEntity tweet = tweetOptional.get();

            CommentEntity comment = new CommentEntity();
            comment.setReplyText(tweetDTO.getText());
            comment.setTimestamp(new Date());
            comment.setUser(user);
            comment.setTweet(tweet);

            tweet.setReplies(tweet.getReplies() + 1);
            tweet.getComments().add(comment);

            tweetRepository.save(tweet);

            return true;
        } else {
            return false;
        }
    }

    public String retweetTweet(Long tweetId, Long userId) {
        if (hasUserRetweeted(tweetId, userId)) {
            return "User has already retweeted this tweet.";
        }

        Optional<UserEntity> userOptional = userRepository.findById(userId);
        Optional<TweetEntity> tweetOptional = tweetRepository.findById(tweetId);

        if (userOptional.isPresent() && tweetOptional.isPresent()) {
            UserEntity user = userOptional.get();
            TweetEntity originalTweet = tweetOptional.get();

            RetweetEntity retweet = new RetweetEntity(user, originalTweet, new Date());
            retweetRepository.save(retweet);

            originalTweet.setRetweets(originalTweet.getRetweets() + 1);
            tweetRepository.save(originalTweet);

            return "Tweet retweeted successfully!";
        } else {
            return "Tweet or user not found.";
        }
    }

    public boolean hasUserRetweeted(Long tweetId, Long userId) {
        return retweetRepository.existsByTweetIdAndUserId(tweetId, userId);
    }

    @Transactional
    public List<TweetDTO> getAllTweetsWithUserDetails() {
        return tweetRepository.getAllTweetsWithUserDetails();
    }

    @Transactional
    public List<TweetDTO> getAllTweetsByUserIdList(Long userId) {
        return tweetRepository.getAllTweetsByUserIdList(userId);
    }

}
