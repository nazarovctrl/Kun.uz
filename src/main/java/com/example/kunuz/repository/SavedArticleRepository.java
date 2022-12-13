package com.example.kunuz.repository;

import com.example.kunuz.entity.article.SavedArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity, Integer> {

    SavedArticleEntity findByArticleIdAndProfileId(String articleId, Integer profileId);

    List<SavedArticleEntity> findByProfileId(Integer profileId);
}
