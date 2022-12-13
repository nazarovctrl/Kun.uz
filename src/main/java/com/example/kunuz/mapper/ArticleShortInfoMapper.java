package com.example.kunuz.mapper;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleShortInfoMapper {
    private String id;
    private String title;
    private String description;
    private String imageId;
    private LocalDateTime publishedDate;

    public ArticleShortInfoMapper(String id, String title, String description, String imageId, LocalDateTime publishedDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageId = imageId;
        this.publishedDate = publishedDate;
    }
}

