package com.example.kunuz.service;

import com.example.kunuz.dto.article.ArticleCreateDTO;
import com.example.kunuz.dto.article.ArticleFilterDTO;
import com.example.kunuz.dto.article.ArticleResponseDTO;
import com.example.kunuz.dto.article.ArticleShortInfoDTO;
import com.example.kunuz.entity.RegionEntity;
import com.example.kunuz.entity.article.ArticleEntity;
import com.example.kunuz.entity.article.ArticleTypeEntity;
import com.example.kunuz.enums.ArticleStatus;
import com.example.kunuz.enums.Language;
import com.example.kunuz.enums.LikeStatus;
import com.example.kunuz.exp.ArticleNotFoundException;
import com.example.kunuz.exp.ArticleNotPublishedException;
import com.example.kunuz.mapper.ArticleShortInfoMapper;
import com.example.kunuz.mapper.IArticleShortInfoMapper;
import com.example.kunuz.repository.ArticleRepository;
import com.example.kunuz.repository.custom.ArticleCustomRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository repository;
    private final ArticleCustomRepository customRepository;

    private final ArticleTypeService articleTypeService;

    private final ProfileService profileService;

    private final RegionService regionService;
    private final AttachService attachService;
    private final ResourceBundleService resourceBundleService;

    public ArticleService(ArticleRepository repository, ArticleCustomRepository customRepository, ArticleTypeService articleTypeService, ProfileService profileService, RegionService regionService, AttachService attachService, ResourceBundleService resourceBundleService) {
        this.repository = repository;
        this.customRepository = customRepository;
        this.articleTypeService = articleTypeService;
        this.profileService = profileService;
        this.regionService = regionService;
        this.attachService = attachService;
        this.resourceBundleService = resourceBundleService;
    }

    public ArticleResponseDTO create(ArticleCreateDTO dto, Integer profileId) {
        ArticleEntity entity = new ArticleEntity();


        entity.setModeratorId(profileId);
        entity.setRegionId(dto.getRegionId());
        entity.setArticleTypeId(dto.getArticleTypeId());
        entity.setImageId(dto.getImageId());

        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);


        repository.save(entity);

        ArticleResponseDTO responseDTO = new ArticleResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setDescription(entity.getDescription());
        responseDTO.setContent(entity.getContent());
        responseDTO.setTitle(entity.getTitle());
        responseDTO.setImageId(entity.getImageId());
        return responseDTO;
    }


    public Boolean update(String id, ArticleCreateDTO dto, Integer profileId, Language language) {

        ArticleEntity entity = getById(id, language);


        entity.setArticleTypeId(dto.getArticleTypeId());
        entity.setRegionId(dto.getRegionId());
        entity.setModeratorId(profileId);


        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setSharedCount(0);
        entity.setViewCount(0);
        entity.setVisible(true);

        entity.setStatus(ArticleStatus.NOT_PUBLISHED);

        repository.save(entity);

        return true;
    }

    public ArticleEntity getById(String id, Language language) {
        Optional<ArticleEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new ArticleNotFoundException(resourceBundleService.getMessage("not.found", language, "Article"));
        }
        return optional.get();
    }

    public boolean delete(String id, Language language) {
        getById(id, language);
        repository.deleteById(id);

        return true;
    }

    public boolean changeStatus(String id, Integer profileId, Language language) {
        ArticleEntity entity = getById(id, language);
        entity.setStatus(ArticleStatus.PUBLISHED);
        entity.setPublisherId(profileId);
        entity.setPublishedDate(LocalDateTime.now());

        repository.save(entity);

        return true;
    }

    public List<ArticleShortInfoDTO> getLast5(Integer id, Language language) {

        ArticleTypeEntity articleType = articleTypeService.getById(id, language);
        List<IArticleShortInfoMapper> iMapperList = repository.findTop5(articleType, ArticleStatus.PUBLISHED);

        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();

        for (IArticleShortInfoMapper iMapper : iMapperList) {
            dtoList.add(getShortDTO(iMapper, language));
        }
        return dtoList;
    }

    private ArticleShortInfoDTO getShortDTO(IArticleShortInfoMapper mapper, Language language) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setTitle(mapper.getTitle());
        dto.setDescription(mapper.getDescription());
        dto.setPublishedDate(mapper.getPublishedDate());
        dto.setPublishedDate(mapper.getPublishedDate());
        dto.setImage(attachService.getById(mapper.getImageId(), language));
        return dto;
    }

    public List<ArticleShortInfoDTO> getLast3(Integer id, Language language) {

        ArticleTypeEntity articleType = articleTypeService.getById(id, language);

        List<IArticleShortInfoMapper> iMapperList = repository.findTop3(articleType, ArticleStatus.PUBLISHED);

        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();

        for (IArticleShortInfoMapper iMapper : iMapperList) {
            dtoList.add(getShortDTO(iMapper, language));
        }

        return dtoList;
    }


    public List<ArticleShortInfoDTO> getLast8(List<String> idList, Language language) {

        List<IArticleShortInfoMapper> iMapperList = repository.getLast8Native(ArticleStatus.PUBLISHED, idList);

        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        iMapperList.forEach(iMapper -> dtoList.add(getShortDTO(iMapper, language)));

        return dtoList;
    }

    public ArticleResponseDTO getByIdAndLang(String id) {
        ArticleEntity entity = repository.findByIdAndStatus(id, ArticleStatus.PUBLISHED);
        if (entity == null) {
            throw new ArticleNotPublishedException("Article not published");
        }

        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());

        dto.setPublishedDate(entity.getPublishedDate());
        dto.setCreatedDate(entity.getCreatedDate());

        dto.setImageId(entity.getImageId());

        dto.setSharedCount(entity.getSharedCount());
        dto.setViewCount(entity.getViewCount());

        dto.setModeratorDTO(profileService.getFullDTO(entity.getModerator()));
        dto.setPublisherDTO(profileService.getFullDTO(entity.getPublisher()));
        dto.setArticleTypeDTO(articleTypeService.getFullDTO(entity.getArticleType()));
        dto.setRegionDTO(regionService.getFullDTO(entity.getRegion()));


        return dto;

    }

    public List<ArticleShortInfoDTO> getLast4ByType1(String id, Integer typeId, Language language) {
        List<IArticleShortInfoMapper> iMapperList = repository.getLast4ByType1(typeId, id, ArticleStatus.PUBLISHED);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        iMapperList.forEach(iMapper -> dtoList.add(getShortDTO(iMapper, language)));
        return dtoList;
    }

    public List<ArticleShortInfoDTO> getTop4(Language language) {
        List<IArticleShortInfoMapper> iMapperList = repository.getTop4();
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        iMapperList.forEach(iMapper -> dtoList.add(getShortDTO(iMapper, language)));
        return dtoList;
    }

    public List<ArticleShortInfoDTO> getLast4ByType2(Integer typeId, Language language) {

        List<IArticleShortInfoMapper> iMapperList = repository.getLast4ByType2(typeId);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        iMapperList.forEach(iMapper -> dtoList.add(getShortDTO(iMapper, language)));
        return dtoList;
    }


    public List<ArticleShortInfoDTO> getLast5ByTypeAndRegion(Integer typeId, String regionKey, Language language) {

        RegionEntity region = regionService.getByKey(regionKey);

        List<IArticleShortInfoMapper> iMapperList = repository.getLast5ByTypeAndRegionKey(typeId, region.getId());
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        iMapperList.forEach(iMapper -> dtoList.add(getShortDTO(iMapper, language)));
        return dtoList;
    }

    public Page<ArticleShortInfoDTO> getListByType(String regionKey, Integer page, Integer size) {
        RegionEntity region = regionService.getByKey(regionKey);

        Sort sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ArticleShortInfoMapper> mapperList = repository.getListByTypeWithPage(region.getId(), pageable);

        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();
        for (ArticleShortInfoMapper mapper : mapperList) {
            ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
            dto.setTitle(mapper.getTitle());
            dto.setDescription(mapper.getDescription());
            dto.setImage(mapper.getImageId());
            dto.setPublishedDate(mapper.getPublishedDate());
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, pageable, mapperList.getTotalElements());
    }


    public void like(String id, Language language) {
        ArticleEntity article = getById(id, language);
        article.setLikeCount(article.getLikeCount() + 1);
        repository.save(article);
    }

    public void dislike(String id, Language language) {
        ArticleEntity article = getById(id, language);
        article.setDislikeCount(article.getDislikeCount() + 1);
        repository.save(article);
    }

    public void remove(String id, LikeStatus status, Language language) {
        ArticleEntity entity = getById(id, language);

        if (status.equals(LikeStatus.LIKE)) {
            entity.setLikeCount(entity.getLikeCount() - 1);
        } else {
            entity.setDislikeCount(entity.getDislikeCount() - 1);
        }

        repository.save(entity);

    }

    public Boolean increaseViewCount(String id, Language language) {
        ArticleEntity entity = getById(id, language);
        entity.setViewCount(entity.getViewCount() + 1);

        repository.save(entity);
        return true;

    }

    public Boolean increaseShareCount(String id, Language language) {
        ArticleEntity entity = getById(id, language);
        entity.setSharedCount(entity.getSharedCount() + 1);
        repository.save(entity);
        return true;
    }

    public Page<ArticleShortInfoDTO> filter(ArticleFilterDTO filterDTO, int page, int size) {
        Page<ArticleEntity> list = customRepository.filter(filterDTO, page, size);

        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();
        for (ArticleEntity entity : list) {
            ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setImage(attachService.getById(entity.getImageId(), Language.EN));
            dto.setPublishedDate(entity.getPublishedDate());

            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, PageRequest.of(page, size), list.getTotalElements());
    }

}
