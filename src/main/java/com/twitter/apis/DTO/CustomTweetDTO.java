package com.twitter.apis.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomTweetDTO {
    private String text;
    private Date timestamp;
    private int likes;
    private int retweets;
    private int replies;

}
