package com.twitter.apis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.apis.service.FeedsService;

@RestController
@RequestMapping("/api")
public class FeedsController {

    private final FeedsService feedsService;

    @Autowired
    public FeedsController(FeedsService feedsService) {
        this.feedsService = feedsService;
    }

    @GetMapping("/feed")
    List<Map<String, Object>> getAllTweetsGroupedByUsers() {
        return feedsService.getAllTweetsGroupedByUsers();
    }

    @GetMapping("/feed/{offset}/{size}")
    public List<Map<String, Object>> getTweets(@PathVariable int offset, @PathVariable int size) {
        List<Map<String, Object>> paginatedTweets = feedsService.getPaginatedTweets(offset, size);
        return paginatedTweets;
    }

}
