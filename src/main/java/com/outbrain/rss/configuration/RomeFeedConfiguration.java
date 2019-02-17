package com.outbrain.rss.configuration;

import com.outbrain.rss.businesslogic.service.FeedReader;
import com.outbrain.rss.infrastructure.rome.RomeFeedReader;
import com.sun.syndication.io.SyndFeedInput;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RomeFeedConfiguration {
    @Bean
    public FeedReader feedReader(){
        return new RomeFeedReader();
    }

    @Bean
    public SyndFeedInput syndFeedInput(){
        return new SyndFeedInput();
    }
}
