package com.example.kunuz.repository;

import com.example.kunuz.entity.article.ArticleLikeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {

    ArticleLikeEntity findByArticleIdAndProfileId(String articleId, Integer profileId);
}
