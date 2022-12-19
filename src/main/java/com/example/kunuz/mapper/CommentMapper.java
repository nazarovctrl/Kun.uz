package com.example.kunuz.mapper;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentMapper {
    private Integer id;



    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer profileId;
    private String name;
    private String surname;

    private String content;
    private String articleId;
    private String title;

    private Integer replyId;


    public CommentMapper(Integer id, LocalDateTime createdDate, LocalDateTime updatedDate, Integer profileId, String name, String surname, String content, String articleId, String title, Integer replyId) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.profileId = profileId;
        this.name = name;
        this.surname = surname;
        this.content = content;
        this.articleId = articleId;
        this.title = title;
        this.replyId = replyId;
    }

    public CommentMapper(Integer id, LocalDateTime createdDate, LocalDateTime updatedDate, Integer profileId, String name, String surname) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.profileId = profileId;
        this.name = name;
        this.surname = surname;
    }
}
