package com.example.kunuz.entity.comment;


import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.entity.article.ArticleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column
    private String content;


    @Column(name = "article_id")
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

    @Column(name = "reply_id")
    private Integer replyId;
    @ManyToOne
    @JoinColumn(name = "reply_id", insertable = false, updatable = false)
    private CommentEntity reply;

    @Column(name = "like_count")
    private Integer likeCount=0;

    @Column(name = "dislike_count")
    private Integer dislikeCount=0;

    @Column
    private Boolean visible = true;
}
