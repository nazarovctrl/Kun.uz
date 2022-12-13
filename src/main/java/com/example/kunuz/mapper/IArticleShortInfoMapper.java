package com.example.kunuz.mapper;

import java.time.LocalDateTime;

public interface IArticleShortInfoMapper {
    String getId();

    String getTitle();

    String getDescription();

    String getImageId();

    LocalDateTime getPublishedDate();
}
