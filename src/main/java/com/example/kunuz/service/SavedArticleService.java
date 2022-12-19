package com.example.kunuz.service;

import com.example.kunuz.dto.article.ArticleShortInfoDTO;
import com.example.kunuz.dto.article.SavedArticleResponseDTO;
import com.example.kunuz.entity.article.ArticleEntity;
import com.example.kunuz.entity.article.SavedArticleEntity;
import com.example.kunuz.enums.Language;
import com.example.kunuz.exp.ArticleAlreadySaved;
import com.example.kunuz.exp.ArticleNotFoundException;
import com.example.kunuz.repository.SavedArticleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SavedArticleService {
    private final SavedArticleRepository repository;
    private final AttachService attachService;

    public SavedArticleService(SavedArticleRepository repository, AttachService attachService) {
        this.repository = repository;
        this.attachService = attachService;

    }

    public Boolean create(String articleId, Integer profileId) {
        SavedArticleEntity exists = repository.findByArticleIdAndProfileId(articleId, profileId);
        if (exists != null) {
            throw new ArticleAlreadySaved("Article Already Saved");
        }

        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticleId(articleId);
        entity.setProfileId(profileId);
        entity.setCreatedDate(LocalDateTime.now());
        repository.save(entity);
        return true;
    }


    public Boolean delete(String articleId, Integer profileId) {
        SavedArticleEntity exists = repository.findByArticleIdAndProfileId(articleId, profileId);
        if (exists == null) {
            throw new ArticleNotFoundException("Saved Article Not Found");
        }

        repository.delete(exists);
        return true;
    }

    public List<SavedArticleResponseDTO> getList(Integer profileId, Language language) {
        List<SavedArticleEntity> entityList = repository.findByProfileId(profileId);
        List<SavedArticleResponseDTO> dtoList = new ArrayList<>();

        for (SavedArticleEntity entity : entityList) {
            SavedArticleResponseDTO dto = new SavedArticleResponseDTO();
            dto.setId(entity.getId());

            ArticleEntity articleEntity = entity.getArticle();

            ArticleShortInfoDTO articleDTO = new ArticleShortInfoDTO();
            articleDTO.setTitle(articleEntity.getTitle());
            articleDTO.setDescription(articleEntity.getDescription());
            articleDTO.setPublishedDate(articleEntity.getPublishedDate());
            articleDTO.setPublishedDate(articleEntity.getPublishedDate());
            articleDTO.setImage(attachService.getById(articleEntity.getImageId(),language));

            dto.setArticleShortInfoDTO(articleDTO);

            dtoList.add(dto);
        }
        return dtoList;

    }
}
