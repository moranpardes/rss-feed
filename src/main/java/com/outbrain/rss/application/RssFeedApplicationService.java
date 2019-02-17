package com.outbrain.rss.application;

import com.outbrain.rss.businesslogic.model.Feed;
import com.outbrain.rss.businesslogic.model.Post;
import com.outbrain.rss.businesslogic.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*

 */
public class RssFeedApplicationService {
    @Autowired
    private FeedService feedService;

    public Feed storeFeed(Feed feed){
        return feedService.saveFeed(feed);
    }

    public List<Post> readPostsFor(Long feedId) {
        return feedService.readPostsFor(feedId);
    }

    public List<Feed> getFeeds(){
        return feedService.getFeeds();
    }
}
