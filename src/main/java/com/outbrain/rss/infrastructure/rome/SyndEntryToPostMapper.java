package com.outbrain.rss.infrastructure.rome;

import com.outbrain.rss.businesslogic.model.Post;
import com.outbrain.rss.infrastructure.util.DateToLocalDateTimeConverter;
import com.sun.syndication.feed.synd.SyndEntry;

class SyndEntryToPostMapper {
    private DateToLocalDateTimeConverter converter = new DateToLocalDateTimeConverter();

    Post map(SyndEntry syndEntry){
        Post post = new Post();
        post.setAuthor(syndEntry.getAuthor());
        post.setTitle(syndEntry.getTitle());
        post.setDate(converter.convert(syndEntry.getPublishedDate()));
        post.setLink(syndEntry.getLink());
        return post;
    }
}
