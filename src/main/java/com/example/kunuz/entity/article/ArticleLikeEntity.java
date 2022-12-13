package com.example.kunuz.entity.article;

import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "article_like", uniqueConstraints = @UniqueConstraint(columnNames = {"profile_id", "article_id"}))
@Getter
@Setter
public class ArticleLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;


    @Column(name = "article_id")
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;


    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column
    @Enumerated(EnumType.STRING)
    private LikeStatus status;
}
