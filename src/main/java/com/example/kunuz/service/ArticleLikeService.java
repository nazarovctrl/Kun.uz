package com.example.kunuz.service;

import com.example.kunuz.entity.article.ArticleLikeEntity;
import com.example.kunuz.enums.Language;
import com.example.kunuz.enums.LikeStatus;
import com.example.kunuz.exp.ArticleLikeAlreadyExists;
import com.example.kunuz.exp.ArticleNotFoundException;
import com.example.kunuz.repository.ArticleLikeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleLikeService {
    private final ArticleLikeRepository repository;
    private final ArticleService articleService;
    private final ResourceBundleService resourceBundleService;

    public ArticleLikeService(ArticleLikeRepository repository, ArticleService articleService, ResourceBundleService resourceBundleService) {
        this.repository = repository;
        this.articleService = articleService;
        this.resourceBundleService = resourceBundleService;
    }

    public Boolean like(String articleId, Integer profileId, Language language) {

        ArticleLikeEntity exists = repository.findByArticleIdAndProfileId(articleId, profileId);
        if (exists != null) {
            throw new ArticleLikeAlreadyExists(resourceBundleService.getMessage("exists", language, "Article like"));
        }

        ArticleLikeEntity entity = new ArticleLikeEntity();
        entity.setArticleId(articleId);
        entity.setProfileId(profileId);
        entity.setStatus(LikeStatus.LIKE);
        entity.setCreatedDate(LocalDateTime.now());

        articleService.like(articleId,language);

        repository.save(entity);

        return true;

    }

    public Boolean dislike(String articleId, Integer profileId, Language language) {

        ArticleLikeEntity exists = repository.findByArticleIdAndProfileId(articleId, profileId);
        if (exists != null) {
            throw new ArticleLikeAlreadyExists(resourceBundleService.getMessage("exists", language, "Article dislike"));
        }

        ArticleLikeEntity entity = new ArticleLikeEntity();
        entity.setArticleId(articleId);
        entity.setProfileId(profileId);
        entity.setStatus(LikeStatus.DISLIKE);
        entity.setCreatedDate(LocalDateTime.now());

        articleService.dislike(articleId,language);

        repository.save(entity);

        return true;
    }


    public Boolean remove(String articleId, Integer profileId, Language language) {
        ArticleLikeEntity entity = repository.findByArticleIdAndProfileId(articleId, profileId);
        if (entity == null) {
            throw new ArticleNotFoundException(resourceBundleService.getMessage("not.found", language, "ArticleLike"));
        }

        articleService.remove(articleId, entity.getStatus(),language);
        repository.delete(entity);
        return true;
    }
}
