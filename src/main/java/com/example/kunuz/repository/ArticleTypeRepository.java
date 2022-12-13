package com.example.kunuz.repository;

import com.example.kunuz.entity.article.ArticleTypeEntity;
import com.example.kunuz.mapper.IArticleTypeMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer>,
        PagingAndSortingRepository<ArticleTypeEntity, Integer> {

    @Query(value = "SELECT a.id as id, a.name_uz as name, a.key as aKey from article_type a", nativeQuery = true)
    List<IArticleTypeMapper> getByLangUz();

    @Query(value = "SELECT a.id as id, a.name_ru as name, a.key as key from article_type a", nativeQuery = true)
    List<IArticleTypeMapper> getByLangRu();

    @Query(value = "SELECT a.id as id, a.name_en as name, a.key as key from article_type a", nativeQuery = true)
    List<IArticleTypeMapper> getByLangEn();
}
