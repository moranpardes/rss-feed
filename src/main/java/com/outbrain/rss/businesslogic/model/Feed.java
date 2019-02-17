package com.outbrain.rss.businesslogic.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "feeds")
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable=false)
    private String name;

    @Column(unique=true, nullable=false)
    private String url;

    public Feed() { }

    public Feed(String name, @NotNull String url) {
        this.name = name;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
