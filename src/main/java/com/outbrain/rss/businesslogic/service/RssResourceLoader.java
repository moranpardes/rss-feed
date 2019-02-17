package com.outbrain.rss.businesslogic.service;

import java.io.InputStream;

public interface RssResourceLoader {

    InputStream load(String url);
}
