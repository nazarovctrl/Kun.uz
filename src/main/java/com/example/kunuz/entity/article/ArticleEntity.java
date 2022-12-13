package com.example.kunuz.entity.article;

import com.example.kunuz.entity.AttachEntity;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.entity.RegionEntity;
import com.example.kunuz.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")

@Getter
@Setter
public class ArticleEntity {

    @Id
    @GeneratedValue(generator = "generator_uuid")
    @GenericGenerator(name = "generator_uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String content;

    @Column(name = "shared_count")
    private Integer sharedCount = 0;

    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;


    @Column(name = "article_type_id")
    private Integer articleTypeId;
    @ManyToOne
    @JoinColumn(name = "article_type_id", insertable = false, updatable = false)
    private ArticleTypeEntity articleType;


    @Column(name = "moderator_id")
    private Integer moderatorId;
    @ManyToOne
    @JoinColumn(name = "moderator_id", insertable = false, updatable = false)
    private ProfileEntity moderator;

    @Column(name = "image_id")
    private String imageId;
    @ManyToOne
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private AttachEntity image;


    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne
    @JoinColumn(name = "publisher_id",insertable = false,updatable = false)
    private ProfileEntity publisher;

    @Enumerated(EnumType.STRING)
    @Column
    private ArticleStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column
    private Boolean visible = true;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column(name = "like_count")
    private Integer likeCount = 0;

    @Column(name = "dislike_count")
    private Integer dislikeCount = 0;
}


