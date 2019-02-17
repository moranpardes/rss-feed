package com.outbrain.rss.businesslogic.service;

import com.outbrain.rss.businesslogic.model.Post;

import java.io.InputStream;
import java.util.List;

public interface FeedReader {
    List<Post> read(InputStream resource);
}
