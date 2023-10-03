package com.twitter.apis.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserProfileDTO {

    private String username;
    private String profilePicture;
    private String bio;
    private int numberOfFollowers;
    private int numberOfTweets;
    @JsonIgnore
    private List<TweetDTO> tweets;
    private List<CustomTweetDTO> filteredTweets;

    public List<CustomTweetDTO> getFilteredTweets() {
        return filteredTweets;
    }

    public void setFilteredTweets(List<CustomTweetDTO> filteredTweets) {
        this.filteredTweets = filteredTweets;
    }

    public UserProfileDTO(List<TweetDTO> tweets) {
        this.tweets = tweets;
    }

    public UserProfileDTO() {
    }

    public UserProfileDTO(String username, String profilePicture, String bio) {
        this.username = username;
        this.profilePicture = profilePicture;
        this.bio = bio;
    }

    public UserProfileDTO(String username, String profilePicture, String bio, int numberOfFollowers,
            int numberOfTweets) {
        this.username = username;
        this.profilePicture = profilePicture;
        this.bio = bio;
        this.numberOfFollowers = numberOfFollowers;
        this.numberOfTweets = numberOfTweets;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    public int getNumberOfTweets() {
        return numberOfTweets;
    }

    public void setNumberOfTweets(int numberOfTweets) {
        this.numberOfTweets = numberOfTweets;
    }

    public List<TweetDTO> getTweets() {
        return tweets;
    }

    public void setTweets(List<TweetDTO> tweets) {
        this.tweets = tweets;
    }

}
