package com.example.kunuz.service;

import com.example.kunuz.entity.article.ArticleLikeEntity;
import com.example.kunuz.enums.LikeStatus;
import com.example.kunuz.exp.ArticleLikeAlreadyExists;
import com.example.kunuz.exp.ArticleLikeNotFound;
import com.example.kunuz.repository.ArticleLikeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleLikeService {
    private final ArticleLikeRepository repository;
    private final ArticleService articleService;

    public ArticleLikeService(ArticleLikeRepository repository, ArticleService articleService) {
        this.repository = repository;
        this.articleService = articleService;
    }

    public Boolean like(String articleId, Integer profileId) {

        ArticleLikeEntity exists = repository.findByArticleIdAndProfileId(articleId, profileId);
        if (exists != null) {
            throw new ArticleLikeAlreadyExists("Article Like Already Exists");
        }

        ArticleLikeEntity entity = new ArticleLikeEntity();
        entity.setArticleId(articleId);
        entity.setProfileId(profileId);
        entity.setStatus(LikeStatus.LIKE);
        entity.setCreatedDate(LocalDateTime.now());

        articleService.like(articleId);

        repository.save(entity);

        return true;

    }

    public Boolean dislike(String articleId, Integer profileId) {

        ArticleLikeEntity exists = repository.findByArticleIdAndProfileId(articleId, profileId);
        if (exists != null) {
            throw new ArticleLikeAlreadyExists("Article Dislike Already Exists");
        }

        ArticleLikeEntity entity = new ArticleLikeEntity();
        entity.setArticleId(articleId);
        entity.setProfileId(profileId);
        entity.setStatus(LikeStatus.DISLIKE);
        entity.setCreatedDate(LocalDateTime.now());

        articleService.dislike(articleId);

        repository.save(entity);

        return true;
    }


    public Boolean remove(String articleId, Integer profileId) {
        ArticleLikeEntity entity = repository.findByArticleIdAndProfileId(articleId, profileId);
        if (entity == null) {
            throw new ArticleLikeNotFound("Article Like not Found");
        }

        articleService.remove(articleId, entity.getStatus());
        repository.delete(entity);
        return true;
    }
}
