package com.twitter.apis.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedsTweetsDTO {

    private String username;
    // private List<TweetDTO> tweets;
    private List<CustomTweetDTO> tweets;

}
