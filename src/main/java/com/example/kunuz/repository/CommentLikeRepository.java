package com.example.kunuz.repository;

import com.example.kunuz.entity.comment.CommentEntity;
import com.example.kunuz.entity.comment.CommentLikeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, Integer> {

    CommentLikeEntity findByProfileIdAndCommentId(Integer profileId, Integer commentId);
}
