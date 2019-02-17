package com.outbrain.rss.businesslogic.service;

import com.outbrain.rss.businesslogic.model.Feed;
import com.outbrain.rss.businesslogic.model.Post;

import java.util.List;

public interface FeedService {
    List<Post> readPostsFor(Long feedId);
    Feed saveFeed(Feed feed);
    List<Feed> getFeeds();
}
