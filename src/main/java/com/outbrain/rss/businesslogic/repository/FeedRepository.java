package com.outbrain.rss.businesslogic.repository;

import com.outbrain.rss.businesslogic.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
