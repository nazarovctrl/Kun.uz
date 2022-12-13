package com.example.kunuz.repository;

import com.example.kunuz.entity.article.ArticleEntity;
import com.example.kunuz.entity.article.ArticleTypeEntity;
import com.example.kunuz.enums.ArticleStatus;
import com.example.kunuz.mapper.ArticleShortInfoMapper;
import com.example.kunuz.mapper.IArticleShortInfoMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, String>,
        PagingAndSortingRepository<ArticleEntity, String> {

    @Query(value = "select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.published_date as publishedDate" +
            " from ArticleEntity a where a.articleType=?1 and a.status=?2 and a.visible=true order by a.createdDate desc limit 5 ", nativeQuery = true)
    List<IArticleShortInfoMapper> findTop5(ArticleTypeEntity articleType, ArticleStatus status);


    @Query(value = "select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.published_date as publishedDate " +
            "from ArticleEntity a where a.articleType=?1 and a.status=?2 and a.visible=true order by a.createdDate desc limit 3", nativeQuery = true)
    List<IArticleShortInfoMapper> findTop3(ArticleTypeEntity articleType, ArticleStatus status);


    @Query(value = "select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.published_date as publishedDate " +
            " from article  a  where a.status =?1 and a.visible =true and a.id not in (?2) order by a.published_date desc limit 8",
            nativeQuery = true)
    List<IArticleShortInfoMapper> getLast8Native(ArticleStatus status, List<String> idList);

//    @Query("select  new com.example.kunuz.mapper.ArticleShortInfoMapper(a.id, a.title, a.description, a.imageId, a.publishedDate)" +
//            " from ArticleEntity  a  where a.status =?1 and a.visible =true and a.id not in (?2) order by a.publishedDate  desc")
//    Page<ArticleShortInfoMapper> getLast8(ArticleStatus status, List<String> idList, Pageable pageable);
//
//    List<ArticleEntity> findTop8ByStatusAndVisibleTrueAndIdNotInOrderByPublishedDateDesc(ArticleStatus status, List<String> idList);

    ArticleEntity findByIdAndStatus(String id, ArticleStatus status);


    @Query(value = "select a.id as id, a.title as title, a.description as description, a.image_id as imageId, a.published_date as publishedDate " +
            "from article a where a.article_type_id=?1 and a.id <>?2 and a.status=?3 order by a.published_date desc limit 4", nativeQuery = true)
    List<IArticleShortInfoMapper> getLast4ByType1(Integer typeId, String id, ArticleStatus status);


    @Query(value = "select a.id as id,a.title as title ,a.description as description ,a.image_id as imageId,a.published_date as publishedDate from article a " +
            "order by a.view_count desc limit 4", nativeQuery = true)
    List<IArticleShortInfoMapper> getTop4();

    @Query(value = "select a.id as id, a.title as title, a.description as description, a.image_id as imageId, a.published_date as publishedDate " +
            "from article a where a.article_type_id=?1 and a.status='PUBLISHED' order by a.published_date desc limit 4", nativeQuery = true)
    List<IArticleShortInfoMapper> getLast4ByType2(Integer typeId);


    @Query(value = "select a.id as id, a.title as title, a.description as description, a.image_id as imageId, a.published_date as publishedDate " +
            "from article a where a.article_type_id=?1 and a.region_id=?2 and a.status='PUBLISHED' order by a.published_date desc limit 4", nativeQuery = true)
    List<IArticleShortInfoMapper> getLast5ByTypeAndRegionKey(Integer typeId, Integer regionId);


    @Query("select new com.example.kunuz.mapper.ArticleShortInfoMapper(a.id,a.title,a.description,a.imageId,a.publishedDate) from ArticleEntity a  " +
            "where a.regionId=?1")
    Page<ArticleShortInfoMapper> getListByTypeWithPage(Integer typeId, Pageable pageable);
}
