package com.twitter.apis.DTO;

import java.util.Date;

public class TweetDTO {
    private Long id;
    private String username;
    private String profilePicture;
    private String text;
    private Date timestamp;
    private int likes;
    private int retweets;
    private int replies;
    private UserDTO user; // User profile details

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public TweetDTO() {
    }

    public TweetDTO(String username, String text, Date timestamp, int likes) {
        this.username = username;
        this.text = text;
        this.timestamp = timestamp;
        this.likes = likes;
    }

    public TweetDTO(String username, String text, Date timestamp, int likes, int replies, int retweets) {
        this.username = username;
        this.text = text;
        this.timestamp = timestamp;
        this.likes = likes;
        this.replies = replies;
        this.retweets = retweets;
    }

    public TweetDTO(Long id, String username, String text, Date timestamp, int likes) {
        this.id = id;
        this.username = username;
        this.text = text;
        this.timestamp = timestamp;
        this.likes = likes;
    }

    public TweetDTO(String text, Date timestamp, String username, int likes, int retweets, int replies) {
        this.text = text;
        this.timestamp = timestamp;
        this.username = username;
        this.likes = likes;
        this.retweets = retweets;
        this.replies = replies;
    }

    public TweetDTO(Long id, String username, String profilePicture, String text, Date timestamp, int likes,
            int retweets) {
        this.id = id;
        this.username = username;
        this.profilePicture = profilePicture;
        this.text = text;
        this.timestamp = timestamp;
        this.likes = likes;
        this.retweets = retweets;
    }

    public TweetDTO(Long id, String username, String profilePicture, String text, Date timestamp) {
        this.id = id;
        this.username = username;
        this.profilePicture = profilePicture;
        this.text = text;
        this.timestamp = timestamp;
    }

    public TweetDTO(Long id, String username, String profilePicture, String text, Date timestamp, int likes,
            int retweets, int replies) {
        this.id = id;
        this.username = username;
        this.profilePicture = profilePicture;
        this.text = text;
        this.timestamp = timestamp;
        this.likes = likes;
        this.retweets = retweets;
        this.replies = replies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

}
