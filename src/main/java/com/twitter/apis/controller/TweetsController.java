package com.twitter.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.apis.DTO.TweetDTO;
import com.twitter.apis.components.UserAuthenticationUtility;
import com.twitter.apis.exceptions.UserOrTweetNotFoundException;
import com.twitter.apis.service.TweetsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tweet")
public class TweetsController {

    private final TweetsService tweetsService;
    private final UserAuthenticationUtility userAuthUtil;

    @Autowired
    public TweetsController(TweetsService tweetsService, UserAuthenticationUtility userAuthUtil) {
        this.tweetsService = tweetsService;
        this.userAuthUtil = userAuthUtil;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTweet(@Valid @RequestBody TweetDTO tweetDTO) {
        Long userId = userAuthUtil.getUserIdFromAuthentication();
        return tweetsService.createTweet(tweetDTO, userId);
    }

    @PostMapping("/{tweetId}/like")
    public ResponseEntity<String> likeTweet(@PathVariable Long tweetId) {
        Long userId = userAuthUtil.getUserIdFromAuthentication();

        try {
            // Like or unlike the tweet
            boolean likeStatusChanged = tweetsService.likeTweet(tweetId, userId);

            if (likeStatusChanged) {
                // Check if the tweet is liked after the operation
                boolean isLikedAfter = tweetsService.isTweetLikedByUser(tweetId, userId);

                if (isLikedAfter) {
                    return ResponseEntity.ok("Tweet liked successfully");
                } else {
                    return ResponseEntity.ok("Tweet unliked successfully");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tweet not found");
            }
        } catch (UserOrTweetNotFoundException e) {
            throw e;
        }
    }

    @PostMapping("/{tweetId}/comment")
    public ResponseEntity<String> commentOnTweet(@PathVariable Long tweetId, @Valid @RequestBody TweetDTO tweetDTO) {
        Long userId = userAuthUtil.getUserIdFromAuthentication();

        try {
            boolean commentStatus = tweetsService.commentOnTweet(tweetId, userId, tweetDTO);

            if (commentStatus) {
                return ResponseEntity.ok("Commented on tweet successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tweet not found");
            }
        } catch (UserOrTweetNotFoundException e) {
            throw e;
        }
    }

    @PostMapping("/{tweetId}/share")
    public ResponseEntity<String> retweet(@PathVariable Long tweetId) {
        Long userId = userAuthUtil.getUserIdFromAuthentication();

        try {
            String retweetStatus = tweetsService.retweetTweet(tweetId, userId);

            if ("Tweet retweeted successfully!".equals(retweetStatus)) {
                return ResponseEntity.ok("Retweeted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retweetStatus);
            }
        } catch (UserOrTweetNotFoundException e) {
            throw e;
        }
    }

    @GetMapping("/alltweets")
    public List<TweetDTO> getAllTweetsWithUserDetails() {
        return tweetsService.getAllTweetsWithUserDetails();
    }

    @GetMapping("/{userId}/tweets")
    public List<TweetDTO> getAllTweetsByUserIdList(@PathVariable Long userId) {
        return tweetsService.getAllTweetsByUserIdList(userId);
    }
}