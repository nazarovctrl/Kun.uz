package com.example.kunuz.service;

import com.example.kunuz.dto.article.ArticleShortDTO;
import com.example.kunuz.dto.comment.CommentCreateDTO;
import com.example.kunuz.dto.comment.CommentResponseDTO;
import com.example.kunuz.dto.comment.CommentUpdateDTO;
import com.example.kunuz.dto.profile.ProfileResponseDTO;
import com.example.kunuz.dto.profile.ProfileShortDTO;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.entity.comment.CommentEntity;
import com.example.kunuz.enums.Language;
import com.example.kunuz.enums.LikeStatus;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.exp.ItemNotFoundException;
import com.example.kunuz.exp.OnlyOwnerCaneUpdate;
import com.example.kunuz.mapper.CommentMapper;
import com.example.kunuz.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentService {
    private final CommentRepository repository;
    private final ResourceBundleService resourceBundleService;

    public CommentService(CommentRepository repository, ResourceBundleService resourceBundleService) {
        this.repository = repository;
        this.resourceBundleService = resourceBundleService;
    }

    public CommentResponseDTO create(CommentCreateDTO dto, Integer profileId) {
        CommentEntity entity = new CommentEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setReplyId(dto.getReplyId());
        entity.setProfileId(profileId);

        entity.setContent(dto.getContent());

        repository.save(entity);

        CommentResponseDTO responseDTO = new CommentResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setCreatedDate(entity.getCreatedDate());
        responseDTO.setContent(entity.getContent());
        responseDTO.setReplyId(entity.getReplyId());
        responseDTO.setArticleId(entity.getArticleId());

        return responseDTO;

    }

    public Boolean update(CommentUpdateDTO dto, Integer profileId, Language language) {
        Optional<CommentEntity> optional = repository.findById(dto.getCommentId());
        if (optional.isEmpty()) {
            throw new ItemNotFoundException(
                    resourceBundleService.getMessage("item.not.found", language, dto.getCommentId()));
        }


        CommentEntity entity = optional.get();
        if (!entity.getProfileId().equals(profileId)) {
            throw new OnlyOwnerCaneUpdate(resourceBundleService.getMessage("owner", language));
        }

        entity.setContent(dto.getContent());
        entity.setUpdatedDate(LocalDateTime.now());

        repository.save(entity);

        return true;
    }

    public Boolean delete(Integer id, ProfileEntity profile, Language language) {
        Optional<CommentEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException(
                    resourceBundleService.getMessage("item.not.found", language, id));
        }

        CommentEntity entity = optional.get();

        if (!entity.getProfileId().equals(profile.getId()) && profile.getRole() != ProfileRole.ROLE_ADMIN) {
            throw new OnlyOwnerCaneUpdate(resourceBundleService.getMessage("owner", language));
        }

        repository.delete(entity);
        return true;
    }

    public List<CommentResponseDTO> getListByArticleId(String articleId) {
        List<CommentMapper> mapperList = repository.getListByArticleId(articleId);

        return getList(mapperList);
    }

    public Page<CommentResponseDTO> getPageList(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CommentMapper> pageObj = repository.getList(pageable);

        List<CommentResponseDTO> dtoList = new ArrayList<>();
        for (CommentMapper commentMapper : pageObj) {
            CommentResponseDTO dto = new CommentResponseDTO();
            dto.setId(commentMapper.getId());
            dto.setCreatedDate(commentMapper.getCreatedDate());
            dto.setUpdatedDate(commentMapper.getUpdatedDate());
            dto.setReplyId(commentMapper.getReplyId());

            ProfileShortDTO profileShortDTO = new ProfileShortDTO();
            profileShortDTO.setId(commentMapper.getProfileId());
            profileShortDTO.setName(commentMapper.getName());
            profileShortDTO.setSurname(commentMapper.getSurname());
            dto.setProfileShortDTO(profileShortDTO);

            ArticleShortDTO articleShortDTO = new ArticleShortDTO();
            articleShortDTO.setId(commentMapper.getArticleId());
            articleShortDTO.setTitle(commentMapper.getTitle());

            dto.setArticleShortDTO(articleShortDTO);

            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    public List<CommentResponseDTO> getReplyList(Integer id) {
        List<CommentMapper> mapperList = repository.getReplyList(id);
        return getList(mapperList);

    }


    public List<CommentResponseDTO> getList(List<CommentMapper> mapperList) {
        List<CommentResponseDTO> dtoList = new ArrayList<>();
        for (CommentMapper commentMapper : mapperList) {
            CommentResponseDTO dto = new CommentResponseDTO();
            dto.setId(commentMapper.getId());
            dto.setCreatedDate(commentMapper.getCreatedDate());
            dto.setUpdatedDate(commentMapper.getUpdatedDate());

            ProfileShortDTO profileShortDTO = new ProfileShortDTO();
            profileShortDTO.setId(commentMapper.getProfileId());
            profileShortDTO.setName(commentMapper.getName());
            profileShortDTO.setSurname(commentMapper.getSurname());

            dto.setProfileShortDTO(profileShortDTO);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public void like(Integer id) {
        Optional<CommentEntity> optional = repository.findById(id);
        CommentEntity entity = optional.get();
        entity.setLikeCount(entity.getLikeCount() + 1);
        repository.save(entity);
    }

    public void dislike(Integer id) {
        Optional<CommentEntity> optional = repository.findById(id);
        CommentEntity entity = optional.get();
        entity.setDislikeCount(entity.getDislikeCount() + 1);
        repository.save(entity);

    }

    public void remove(Integer id, LikeStatus status) {
        Optional<CommentEntity> optional = repository.findById(id);
        CommentEntity entity = optional.get();


        if (status.equals(LikeStatus.DISLIKE)) {
            entity.setDislikeCount(entity.getDislikeCount() - 1);
        } else if (status.equals(LikeStatus.LIKE)) {
            entity.setLikeCount(entity.getLikeCount() - 1);
        }

        repository.save(entity);

    }


}
