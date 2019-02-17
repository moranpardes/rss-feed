package com.outbrain.rss.controller;

import com.outbrain.rss.application.RssFeedApplicationService;
import com.outbrain.rss.businesslogic.model.Feed;
import com.outbrain.rss.businesslogic.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class RssFeedController {
    @Autowired
    private RssFeedApplicationService service;

    @GetMapping("/feeds/{id}/posts")
    @ResponseBody
    public List<Post> getPosts(@PathVariable(name = "id") Long feedId) {
        List<Post> posts = service.readPostsFor(feedId);
        return posts;
    }

    @PostMapping("/feeds")
    @ResponseBody
    public Feed storeFeed(@RequestBody Feed feed){
        return service.storeFeed(feed);
    }

    @GetMapping("/feeds")
    @ResponseBody
    public List<Feed> getFeeds() {
        List<Feed> feeds = service.getFeeds();
        return feeds;
    }

}
