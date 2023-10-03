package com.twitter.apis.DTO;

import java.util.Date;

import com.twitter.apis.model.TweetEntity;
import com.twitter.apis.model.UserEntity;

public class RetweetsDTO {

    private UserEntity user;
    private TweetEntity originalTweet;
    private Date retweetTimestamp;

    public RetweetsDTO(UserEntity user, TweetEntity originalTweet, Date retweetTimestamp) {
        this.user = user;
        this.originalTweet = originalTweet;
        this.retweetTimestamp = retweetTimestamp;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TweetEntity getOriginalTweet() {
        return originalTweet;
    }

    public void setOriginalTweet(TweetEntity originalTweet) {
        this.originalTweet = originalTweet;
    }

    public Date getRetweetTimestamp() {
        return retweetTimestamp;
    }

    public void setRetweetTimestamp(Date retweetTimestamp) {
        this.retweetTimestamp = retweetTimestamp;
    }

}
