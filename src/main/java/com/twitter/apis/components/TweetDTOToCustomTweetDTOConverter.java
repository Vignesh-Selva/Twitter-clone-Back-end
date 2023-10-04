package com.twitter.apis.components;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.twitter.apis.DTO.CustomTweetDTO;
import com.twitter.apis.DTO.TweetDTO;

@Component
public class TweetDTOToCustomTweetDTOConverter implements Converter<TweetDTO, CustomTweetDTO> {

    @Override
    public CustomTweetDTO convert(TweetDTO tweetDTO) {
        CustomTweetDTO customTweetDTO = new CustomTweetDTO();
        customTweetDTO.setText(tweetDTO.getText());
        customTweetDTO.setTimestamp(tweetDTO.getTimestamp());
        customTweetDTO.setLikes(tweetDTO.getLikes());
        customTweetDTO.setReplies(tweetDTO.getReplies());
        customTweetDTO.setRetweets(tweetDTO.getRetweets());

        return customTweetDTO;
    }

}
