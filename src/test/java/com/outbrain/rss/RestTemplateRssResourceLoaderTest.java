package com.outbrain.rss;

import com.outbrain.rss.businesslogic.service.RssResourceLoader;
import com.outbrain.rss.configuration.ResourceLoaderConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ResourceLoaderConfiguration.class)
public class RestTemplateRssResourceLoaderTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private RssResourceLoader rssResourceLoader;
    @Test
    public void loadRssResource() throws IOException {
        when(restTemplate.getForEntity("url", Resource.class)).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        InputStream url = rssResourceLoader.load("url");
        assertThat(url.available()).isZero();
    }

}
