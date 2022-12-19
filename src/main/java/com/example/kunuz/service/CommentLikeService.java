package com.example.kunuz.service;


import com.example.kunuz.entity.comment.CommentLikeEntity;
import com.example.kunuz.enums.LikeStatus;
import com.example.kunuz.exp.CommentLikeNotFound;
import com.example.kunuz.exp.CommentLikeAlreadyExists;
import com.example.kunuz.repository.CommentLikeRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {

    private final CommentLikeRepository repository;
    private final CommentService commentService;

    public CommentLikeService(CommentLikeRepository repository, CommentService commentService) {
        this.repository = repository;
        this.commentService = commentService;
    }

    public boolean like(Integer id, Integer profileId) {

        CommentLikeEntity exists = repository.findByProfileIdAndCommentId(profileId, id);
        if (exists != null) {
            throw new CommentLikeAlreadyExists("Comment like already exists");
        }

        CommentLikeEntity entity = new CommentLikeEntity();
        entity.setCommentId(id);
        entity.setProfileId(profileId);
        entity.setStatus(LikeStatus.LIKE);

        commentService.like(id);

        repository.save(entity);
        return true;
    }

    public boolean dislike(Integer id, Integer profileId) {
        CommentLikeEntity exists = repository.findByProfileIdAndCommentId(profileId, id);
        if (exists != null) {
            throw new CommentLikeAlreadyExists("Comment like already exists");
        }
        CommentLikeEntity entity = new CommentLikeEntity();
        entity.setCommentId(id);
        entity.setProfileId(profileId);
        entity.setStatus(LikeStatus.DISLIKE);
        commentService.dislike(id);

        repository.save(entity);
        return true;


    }

    public boolean remove(Integer id, Integer profileId) {
        CommentLikeEntity entity = repository.findByProfileIdAndCommentId(profileId, id);

        if (entity == null) {
            throw new CommentLikeNotFound("Comment like not found");
        }

        commentService.remove(id, entity.getStatus());
        repository.delete(entity);
        return true;

    }


}
