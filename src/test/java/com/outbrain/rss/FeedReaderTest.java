package com.outbrain.rss;

import com.outbrain.rss.businesslogic.exception.ReadFeedException;
import com.outbrain.rss.businesslogic.model.Post;
import com.outbrain.rss.businesslogic.service.FeedReader;
import com.outbrain.rss.configuration.RomeFeedConfiguration;
import org.apache.commons.io.input.NullInputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = RomeFeedConfiguration.class)
public class FeedReaderTest {
    @Value("classpath:rss.xml")
    Resource rssXMLResource;

    @Value("classpath:rss-without-links.xml")
    Resource rssWithoutLinksXMLResource;

    @Value("classpath:invalid-xml")
    Resource rssInvalidXMLResource;

    @Autowired
    private FeedReader feedReader;

    @Test
    public void readFeed() throws IOException {
        List<Post> posts = feedReader.read(rssXMLResource.getInputStream());

        assertThat(posts.size()).isOne();
        assertThat(posts.get(0).getAuthor()).isEqualTo("");
        assertThat(posts.get(0).getTitle()).isEqualTo("title");
        assertThat(posts.get(0).getDate()).isEqualTo(LocalDateTime.parse("2019-02-11T14:00"));
        assertThat(posts.get(0).getLink()).isEqualTo("https://domain.com/index.html");

    }

    @Test
    public void readEmptyFeed() {
        List<Post> posts = feedReader.read(new NullInputStream(0));
        assertThat(posts.size()).isZero();
    }

    @Test
    public void readFeedWithoutLinks() throws IOException {
        List<Post> posts = feedReader.read(rssWithoutLinksXMLResource.getInputStream());
        assertThat(posts.size()).isZero();
    }

    @Test(expected = ReadFeedException.class)
    public void readInvalidRssFeed() throws IOException {
        feedReader.read(rssInvalidXMLResource.getInputStream());
    }

}
