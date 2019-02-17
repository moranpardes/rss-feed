package com.outbrain.rss.configuration;

import com.outbrain.rss.businesslogic.service.RssResourceLoader;
import com.outbrain.rss.infrastructure.RestTemplateRssResourceLoader;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ResourceLoaderConfiguration {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RssResourceLoader rssResourceLoader(){
        return new RestTemplateRssResourceLoader();
    }
}
