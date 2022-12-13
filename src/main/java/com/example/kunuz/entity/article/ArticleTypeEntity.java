package com.example.kunuz.entity.article;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "article_type")

@Getter
@Setter
public class ArticleTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String key;

    @Column
    private String nameUz;

    @Column
    private String nameRu;

    @Column
    private String nameEn;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public ArticleTypeEntity() {
    }

    public ArticleTypeEntity(Integer id) {
        this.id = id;
    }
}
