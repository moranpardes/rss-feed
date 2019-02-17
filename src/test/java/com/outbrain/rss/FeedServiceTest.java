package com.outbrain.rss;

import com.outbrain.rss.businesslogic.exception.FeedNotFoundException;
import com.outbrain.rss.businesslogic.exception.InvalidFeedException;
import com.outbrain.rss.businesslogic.model.Feed;
import com.outbrain.rss.businesslogic.model.Post;
import com.outbrain.rss.businesslogic.repository.FeedRepository;
import com.outbrain.rss.businesslogic.repository.PostRepository;
import com.outbrain.rss.businesslogic.service.FeedReader;
import com.outbrain.rss.businesslogic.service.FeedService;
import com.outbrain.rss.businesslogic.service.RssResourceLoader;
import com.outbrain.rss.businesslogic.validator.FeedValidator;
import com.outbrain.rss.configuration.RssFeedConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJpaRepositories
@EnableAutoConfiguration
@ContextConfiguration(classes = RssFeedConfiguration.class)
@Import(RssFeedConfiguration.class)
public class FeedServiceTest {
    @Autowired
    private FeedService feedService;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private PostRepository postRepository;

    @MockBean
    private FeedValidator feedValidator;

    @MockBean
    private FeedReader feedReader;

    @MockBean
    private RssResourceLoader rssResourceLoader;

    @Test
    @DirtiesContext
    public void saveFeed(){
        Feed feed = new Feed("name", "url");
        when(feedValidator.isValid(any())).thenReturn(true);
        feedService.saveFeed(feed);

        Optional<Feed> savedFeed = feedRepository.findById(feed.getId());

        assertThat(savedFeed.isPresent()).isTrue();
        assertThat(savedFeed.get().getName()).isEqualTo("name");
        assertThat(savedFeed.get().getUrl()).isEqualTo("url");
    }

    @Test
    @DirtiesContext
    public void dontSaveInvalidFeed(){
        Feed feed = new Feed("name", "url");
        when(feedValidator.isValid(any())).thenReturn(false);
        try {
            feedService.saveFeed(feed);
        }catch (InvalidFeedException ex){
            List<Feed> feeds = feedRepository.findAll();
            assertThat(feeds).isEmpty();
        }
    }

    @Test(expected = FeedNotFoundException.class)
    @DirtiesContext
    public void readPostOfMissingFeed(){
        feedService.readPostsFor(1L);
    }

    @Test
    @DirtiesContext
    public void readPostsWithUpdatedData() {
        Feed feed = new Feed("name", "url");
        feedRepository.save(feed);
        LocalDateTime dateTime = LocalDateTime.now();
        Post post = new Post();
        post.setTitle("title");
        post.setAuthor("author");
        post.setDate(dateTime);
        post.setLink("link");
        post.setFeed(feed);
        postRepository.save(post);

        Post updatedPost = new Post();
        updatedPost.setTitle("new title");
        updatedPost.setAuthor("new author");
        updatedPost.setDate(dateTime.plusDays(1));
        updatedPost.setLink("link");
        when(rssResourceLoader.load(any())).thenReturn(null);
        when(feedReader.read(any())).thenReturn(Collections.singletonList(updatedPost));

        List<Post> posts = feedService.readPostsFor(feed.getId());
        assertThat(posts.size()).isOne();
        assertThat(posts.get(0).getTitle()).isEqualTo("new title");
        assertThat(posts.get(0).getAuthor()).isEqualTo("new author");
        assertThat(posts.get(0).getDate()).isEqualTo(dateTime.plusDays(1));
        assertThat(posts.get(0).getLink()).isEqualTo("link");
    }

    @Test
    @DirtiesContext
    public void readPostsWithNewData() {
        Feed feed = new Feed("name", "url");
        feedRepository.save(feed);
        LocalDateTime dateTime = LocalDateTime.now();
        Post post = new Post();
        post.setTitle("title");
        post.setAuthor("author");
        post.setDate(dateTime);
        post.setLink("link");
        post.setFeed(feed);
        postRepository.save(post);

        Post newPost = new Post();
        newPost.setTitle("new title");
        newPost.setAuthor("new author");
        newPost.setDate(dateTime.plusDays(1));
        newPost.setLink("new link");

        when(rssResourceLoader.load(any())).thenReturn(null);
        when(feedReader.read(any())).thenReturn(Collections.singletonList(newPost));

        List<Post> posts = feedService.readPostsFor(feed.getId());
        assertThat(posts.size()).isEqualTo(2);
        assertThat(posts).extracting("title").containsExactlyInAnyOrder("title", "new title");
        assertThat(posts).extracting("author").containsExactlyInAnyOrder("author", "new author");
        assertThat(posts).extracting("date").containsExactlyInAnyOrder(dateTime, dateTime.plusDays(1));
        assertThat(posts).extracting("link").containsExactlyInAnyOrder("link", "new link");
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext
    public void tryToSaveTheSameFeedTwice(){
        Feed feed1 = new Feed("name", "url");
        Feed feed2 = new Feed("name", "url");
        when(feedValidator.isValid(any())).thenReturn(true);
        feedService.saveFeed(feed1);
        feedService.saveFeed(feed2);
    }


}
