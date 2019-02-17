package com.outbrain.rss;

import com.outbrain.rss.application.RssFeedApplicationService;
import com.outbrain.rss.businesslogic.exception.FeedNotFoundException;
import com.outbrain.rss.businesslogic.exception.InvalidFeedException;
import com.outbrain.rss.businesslogic.exception.ReadFeedException;
import com.outbrain.rss.businesslogic.model.Feed;
import com.outbrain.rss.businesslogic.model.Post;
import com.outbrain.rss.controller.RestExceptionHandler;
import com.outbrain.rss.controller.RssFeedController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RssFeedController.class)
@ContextConfiguration(classes = {RssFeedControllerTest.EmptyConfiguration.class, RssFeedController.class, RestExceptionHandler.class})
public class RssFeedControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RssFeedApplicationService service;

    private Feed feed;
    private String feedJsonReq;
    private String feedJsonRes;
    private Post post;
    private String postJsonRes;

    @Before
    public void before(){
        feed = new Feed();
        feed.setId(1);
        feed.setName("feed name");
        feed.setUrl("feed url");
        feedJsonReq = "{ \"name\": \"feed name\", \"url\": \"feed url\"}";
        feedJsonRes = "{ \"id\": 1, \"name\": \"feed name\", \"url\": \"feed url\"}";

        post = new Post();
        post.setId(1L);
        post.setAuthor("post author");
        post.setTitle("post title");
        post.setDate(LocalDateTime.parse("2019-02-11T20:52:34"));
        post.setLink("https://domain.com/index.html");
        postJsonRes = "{\"id\": 1, \"author\": \"post author\", \"title\": \"post title\", \"date\": \"2019-02-11T20:52:34\", \"link\": \"https://domain.com/index.html\"}";
    }

    @Test
    public void storeFeed() throws Exception {
        when(service.storeFeed(any())).thenReturn(feed);
        callStoreFeedApi(feedJsonReq).andExpect(status().isOk()).andExpect(content().json(feedJsonRes));
    }

    @Test
    public void tryToStoreInvalidFeed() throws Exception {
        when(service.storeFeed(any())).thenThrow(InvalidFeedException.class);
        callStoreFeedApi("{}").andExpect(status().isBadRequest());
    }

    @Test
    public void getPosts() throws Exception {
        when(service.readPostsFor(anyLong())).thenReturn(Collections.singletonList(post));
        callGetPosts().andExpect(status().isOk()).andExpect(content().json("["+ postJsonRes + "]"));
    }

    @Test
    public void tryToGetPostsWithFeedNotFoundException() throws Exception {
        when(service.readPostsFor(anyLong())).thenThrow(FeedNotFoundException.class);
        callGetPosts().andExpect(status().isNotFound());
    }

    @Test
    public void tryToGetPostsWithReadFeedException() throws Exception {
        when(service.readPostsFor(anyLong())).thenThrow(ReadFeedException.class);
        callGetPosts().andExpect(status().isInternalServerError());
    }

    private ResultActions callGetPosts() throws Exception {
        return mvc.perform(
                get("/feeds/1/posts"));
    }

    private ResultActions callStoreFeedApi(String body) throws Exception {
        return mvc.perform(
                post("/feeds")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }

    @Configuration
    static class EmptyConfiguration{

    }
}
