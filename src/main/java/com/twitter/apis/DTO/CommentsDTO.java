package com.twitter.apis.DTO;

public class CommentsDTO {
    private Long id;
    private Long userId;
    private Long tweetId;
    private String replyText;
    private String timestamp;

    // public CommentsDTO(Long id, Long userId, Long tweetId, String replyText, String timestamp) {
    //     this.id = id;
    //     this.userId = userId;
    //     this.tweetId = tweetId;
    //     this.replyText = replyText;
    //     this.timestamp = timestamp;
    // }

    public CommentsDTO(Long userId, Long tweetId, String replyText, String timestamp) {
        this.userId = userId;
        this.tweetId = tweetId;
        this.replyText = replyText;
        this.timestamp = timestamp;
    }

    public CommentsDTO() {
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public String getReplyText() {
        return replyText;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
