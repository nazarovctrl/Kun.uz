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
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "reply_id")
    private CommentEntity reply;

    @Column
    private Boolean visible;
}
