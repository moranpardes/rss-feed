package com.outbrain.rss.infrastructure.rome;

import com.outbrain.rss.businesslogic.exception.ReadFeedException;
import com.outbrain.rss.businesslogic.model.Post;
import com.outbrain.rss.businesslogic.service.FeedReader;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class RomeFeedReader implements FeedReader {
    private static final boolean RELAXED_CHARSET_ENCODING = true;
    @Autowired
    private SyndFeedInput syndFeedInput;

    private SyndEntryToPostMapper mapper = new SyndEntryToPostMapper();

    @Override
    public List<Post> read(InputStream inputStream){
        try {
            if (inputStream == null || inputStream.available() == 0)
                return Collections.emptyList();

            SyndFeed feed = syndFeedInput.build(new XmlReader(inputStream, RELAXED_CHARSET_ENCODING));
            List<SyndEntry> entries = feed.getEntries();
            return entries.stream()
                    .filter(syndEntry -> syndEntry.getLink() != null && !syndEntry.getLink().isEmpty())
                    .map(syndEntry -> mapper.map(syndEntry))
                    .collect(toList());

        } catch (FeedException | IOException ex) {
            throw new ReadFeedException("Failed to read from feed", ex.getCause());
        }
    }
}
