package com.outbrain.rss.infrastructure;

import com.outbrain.rss.businesslogic.exception.RssResourceLoderException;
import com.outbrain.rss.businesslogic.service.RssResourceLoader;
import org.apache.commons.io.input.NullInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class RestTemplateRssResourceLoader implements RssResourceLoader {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public InputStream load(String url) {
        try {
            ResponseEntity<Resource> responseEntity = restTemplate.getForEntity(url, Resource.class);
            if (responseEntity.hasBody())
                return Objects.requireNonNull(responseEntity.getBody()).getInputStream();
            return new NullInputStream(0);
        } catch (RestClientException | IOException e) {
            throw new RssResourceLoderException("Failed to read content from " + url, e);
        }
    }
}
