package com.example.kunuz.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ArticleShortInfoDTO {

    private String title;
    private String description;
    private LocalDateTime publishedDate;
    private String image;


}
