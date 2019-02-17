package com.outbrain.rss.businesslogic.validator;

import com.outbrain.rss.businesslogic.model.Feed;
import org.apache.commons.validator.routines.UrlValidator;

public class FeedValidatorImpl implements  FeedValidator{
    private  UrlValidator urlValidator = new UrlValidator();

    @Override
    public boolean isValid(Feed feed) {
        return feed != null && urlValidator.isValid(feed.getUrl());
    }
}
