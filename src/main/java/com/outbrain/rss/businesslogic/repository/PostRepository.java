package com.outbrain.rss.businesslogic.repository;

import com.outbrain.rss.businesslogic.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByFeedIdOrderByCreatedAtDesc(Long id);
}