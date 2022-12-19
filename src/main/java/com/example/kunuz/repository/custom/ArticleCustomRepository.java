package com.example.kunuz.repository.custom;


import com.example.kunuz.dto.article.ArticleFilterDTO;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.entity.article.ArticleEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public Page<ArticleEntity> filter(ArticleFilterDTO filter, int page, int size) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder builder = new StringBuilder("Select p from ArticleEntity p ");
        builder.append(" where 1 = 1 ");

        StringBuilder builderCount = new StringBuilder("Select count(p) from ArticleEntity p where 1 = 1  ");

        if (filter.getTitle() != null) {
            builder.append(" and p.title=:title ");
            builderCount.append(" and p.title=:title ");
            params.put("title", filter.getTitle());
        }

        if (filter.getRegionId() != null) {
            builder.append(" and p.region_id=:regionId ");
            builderCount.append(" and p.region_id=:regionId ");
            params.put("regionId", filter.getRegionId());
        }

        if (filter.getStatus() != null) {
            builder.append(" and p.status=:status ");
            builderCount.append(" and p.status=:status ");
            params.put("status", filter.getStatus());
        }

        if (filter.getArticleTypeId() != null) {
            builder.append(" and p.article_type_id=:articleTypeId ");
            builderCount.append(" and p.article_type_id=:articleTypeId ");
            params.put("articleTypeId", filter.getArticleTypeId());

        }

        if (filter.getModeratorId() != null) {
            builder.append(" and p.moderator_id=:moderatorId ");
            builderCount.append(" and p.moderator_id=:moderatorId ");
            params.put("moderatorId", filter.getModeratorId());

        }
        if (filter.getPublisherId() != null) {
            builder.append(" and p.publisher_id=:publisherId ");
            builderCount.append(" and p.publisher_id=:publisherId ");
            params.put("publisherId", filter.getModeratorId());
        }


        if (filter.getFromDate() != null && filter.getToDate() != null) {
            // (SELECT cast(created_date as date))=?1",
            // to_date(to_char(created_date, 'YYYY/MM/DD'), 'YYYY/MM/DD')=?1
            builder.append(" and  p.published_date  between :fromDate and :toDate ");
            builderCount.append(" and  p.published_date  between :fromDate and :toDate ");
            params.put("fromDate", filter.getFromDate().atStartOfDay());
            params.put("toDate", filter.getToDate().atStartOfDay());
        } else if (filter.getFromDate() != null) {
            builder.append(" and p.published_date  >= :fromDate");
            builderCount.append(" and p.published_date  >= :fromDate");
            params.put("fromDate", filter.getFromDate().atStartOfDay());
        } else if (filter.getToDate() != null) {
            builder.append(" and  p.published_date  <= :toDate");
            builderCount.append(" and  p.published_date  <= :toDate");
            params.put("toDate", LocalDateTime.of(filter.getFromDate(), LocalTime.MAX));
        }
        //content
        Query query = this.entityManager.createQuery(builder.toString());
        query.setFirstResult((page) * size); // offset 40
        query.setMaxResults(size); // limit 20

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        List<ArticleEntity> list = query.getResultList();

        // totalCount
        query = this.entityManager.createQuery(builderCount.toString());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        Long totalCount = (Long) query.getSingleResult();

        return new PageImpl<>(list, PageRequest.of(page, size), totalCount);
    }

    public List<ArticleEntity> getAll(String q) {
        Query query = this.entityManager.createQuery(q);
        List profileEntities = query.getResultList();
        return profileEntities;
    }

    public List<ArticleEntity> getAllNative() {
        Query query = entityManager.createNativeQuery("SELECT * FROM profile ", ArticleEntity.class);
        List profileEntities = query.getResultList();
        return profileEntities;
    }
}
