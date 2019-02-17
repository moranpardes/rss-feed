package com.outbrain.rss.businesslogic.service;

import com.outbrain.rss.businesslogic.exception.FeedNotFoundException;
import com.outbrain.rss.businesslogic.exception.InvalidFeedException;
import com.outbrain.rss.businesslogic.model.Feed;
import com.outbrain.rss.businesslogic.model.Post;
import com.outbrain.rss.businesslogic.repository.FeedRepository;
import com.outbrain.rss.businesslogic.repository.PostRepository;
import com.outbrain.rss.businesslogic.validator.FeedValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class FeedServiceImpl implements FeedService{

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FeedReader feedReader;

    @Autowired
    private FeedValidator feedValidator;

    @Autowired
    private RssResourceLoader rssResourceLoader;

    @Override
    public List<Post> readPostsFor(Long feedId) {
        Feed feed = findFeed(feedId);
        List<Post> posts = readPostsFromFeed(feed);
        postRepository.saveAll(posts);
        return postRepository.findAllByFeedIdOrderByCreatedAtDesc(feed.getId());
    }

    @Override
    public Feed saveFeed(Feed feed) {
        if (!feedValidator.isValid(feed)) {
            String errorMsg = String.format("The supplied url is invalid [%s]", feed.getUrl());
            throw new InvalidFeedException(errorMsg);
        }

        return feedRepository.save(feed);
    }

    @Override
    public List<Feed> getFeeds(){
        return feedRepository.findAll();
    }

    private Feed findFeed(Long feedId){
        Optional<Feed> feedOptional = feedRepository.findById(feedId);
        if (!feedOptional.isPresent()) {
            String errMsg = String.format("Could not find Feed with id: %s", feedId);
            throw new FeedNotFoundException(errMsg);
        }
        return  feedOptional.get();
    }

    private List<Post> readPostsFromFeed(Feed feed){
        List<Post> posts = feedReader.read(rssResourceLoader.load(feed.getUrl()));
        posts.forEach(post -> post.setFeed(feed));
        return posts;
    }
}
