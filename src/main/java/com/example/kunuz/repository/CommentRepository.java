package com.example.kunuz.repository;

import com.example.kunuz.entity.comment.CommentEntity;
import com.example.kunuz.mapper.CommentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Integer>, PagingAndSortingRepository<CommentEntity, Integer> {

    @Query("select new com.example.kunuz.mapper.CommentMapper(c.id,c.createdDate,c.updatedDate," +
            "c.profileId,c.profile.name,c.profile.surname) from CommentEntity c " +
            "where c.articleId=?1 ")
    List<CommentMapper> getListByArticleId(String articleId);

    @Query("select new com.example.kunuz.mapper.CommentMapper(c.id,c.createdDate,c.updatedDate," +
            "c.profileId,c.profile.name,c.profile.surname,c.content,c.articleId,c.article.title,c.replyId) from CommentEntity c ")
    Page<CommentMapper> getList(Pageable pageable);


    @Query("select new com.example.kunuz.mapper.CommentMapper(c.id,c.createdDate,c.updatedDate," +
            "c.profileId,c.profile.name,c.profile.surname) from CommentEntity c " +
            "where c.replyId=?1 ")
    List<CommentMapper> getReplyList(Integer id);


}
