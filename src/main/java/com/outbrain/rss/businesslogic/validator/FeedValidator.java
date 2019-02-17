package com.outbrain.rss.businesslogic.validator;

import com.outbrain.rss.businesslogic.model.Feed;

public interface FeedValidator {
    boolean isValid(Feed feed);
}
