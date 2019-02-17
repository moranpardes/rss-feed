package com.outbrain.rss.configuration;

import com.outbrain.rss.application.RssFeedApplicationService;
import com.outbrain.rss.businesslogic.service.FeedService;
import com.outbrain.rss.businesslogic.service.FeedServiceImpl;
import com.outbrain.rss.businesslogic.validator.FeedValidator;
import com.outbrain.rss.businesslogic.validator.FeedValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RssFeedConfiguration {
    @Bean
    public RssFeedApplicationService rssFeedApplicationService(){
        return new RssFeedApplicationService();
    }

    @Bean
    public FeedService feedService(){
        return new FeedServiceImpl();
    }

    @Bean
    public FeedValidator feedValidator(){
        return new FeedValidatorImpl();
    }
}
