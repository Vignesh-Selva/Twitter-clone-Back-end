package com.twitter.apis.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.twitter.apis.DTO.TweetDTO;
import com.twitter.apis.model.UserEntity;
import com.twitter.apis.repository.TweetRepository;
import com.twitter.apis.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FeedsService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Autowired
    public FeedsService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    // Get all tweets grouped by users
    public List<Map<String, Object>> getAllTweetsGroupedByUsers() {
        List<TweetDTO> allTweets = tweetRepository.getAllTweetsWithUserDetails();

        List<Map<String, Object>> userTweetsList = new ArrayList<>();

        for (TweetDTO tweet : allTweets) {
            Map<String, Object> userObject = new LinkedHashMap<>();

            Optional<UserEntity> userOptional = userRepository.findByUsername(tweet.getUsername());
            UserEntity user = userOptional.orElse(new UserEntity());

            userObject.put("username", user.getUsername());
            userObject.put("profilePicture", user.getProfilePicture());
            userObject.put("bio", user.getBio());
            userObject.put("numberOfFollowers", user.getNumberOfFollowers());

            Map<String, Object> tweetMap = new LinkedHashMap<>();
            tweetMap.put("text", tweet.getText());
            tweetMap.put("timestamp", tweet.getTimestamp());
            tweetMap.put("likes", tweet.getLikes());
            tweetMap.put("retweets", tweet.getRetweets());
            tweetMap.put("replies", tweet.getReplies());

            userObject.put("tweet", tweetMap);

            userTweetsList.add(userObject);
        }

        return userTweetsList;
    }

    // Get paginated tweets
    public List<Map<String, Object>> getPaginatedTweets(int offset, int size) {
        try {
            Pageable pageable = PageRequest.of(offset, size, Sort.by(Sort.Order.desc("timestamp")));
            List<TweetDTO> tweetDTOs = tweetRepository.findAllByOrderByTimestampDesc(pageable);

            List<Map<String, Object>> userTweetsList = new ArrayList<>();

            for (TweetDTO tweetDTO : tweetDTOs) {
                Map<String, Object> userObject = new LinkedHashMap<>();
                String username = tweetDTO.getUsername();

                Optional<UserEntity> userOptional = userRepository.findByUsername(username);
                UserEntity user = userOptional.orElseThrow(() -> new EntityNotFoundException("User not found"));

                userObject.put("username", user.getUsername());
                userObject.put("profilePicture", user.getProfilePicture());
                userObject.put("bio", user.getBio());
                userObject.put("numberOfFollowers", user.getNumberOfFollowers());

                Map<String, Object> tweetMap = new LinkedHashMap<>();
                tweetMap.put("text", tweetDTO.getText());
                tweetMap.put("timestamp", tweetDTO.getTimestamp());
                tweetMap.put("likes", tweetDTO.getLikes());
                tweetMap.put("retweets", tweetDTO.getRetweets());
                tweetMap.put("replies", tweetDTO.getReplies());

                userObject.put("tweet", tweetMap);

                userTweetsList.add(userObject);
            }

            return userTweetsList;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching paginated tweets.", e);
        }
    }

}
