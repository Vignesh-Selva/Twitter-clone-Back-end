package com.twitter.apis.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "retweets")
public class RetweetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private TweetEntity tweet;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public RetweetEntity(UserEntity user, TweetEntity tweet, Date timestamp) {
        this.user = user;
        this.tweet = tweet;
        this.timestamp = timestamp;
    }
}