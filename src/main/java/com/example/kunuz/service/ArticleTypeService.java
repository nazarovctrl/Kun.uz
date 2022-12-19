package com.example.kunuz.service;

import com.example.kunuz.dto.article.ArticleTypeShortDTO;
import com.example.kunuz.dto.article.ArticleTypeDTO;
import com.example.kunuz.entity.article.ArticleTypeEntity;
import com.example.kunuz.enums.Language;
import com.example.kunuz.exp.ArticleTypeNotFoundException;
import com.example.kunuz.mapper.IArticleTypeMapper;
import com.example.kunuz.repository.ArticleTypeRepository;
import com.example.kunuz.util.LangUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    private final ArticleTypeRepository repository;

    private final ResourceBundleService resourceBundleService;

    public ArticleTypeService(ArticleTypeRepository repository, ResourceBundleService resourceBundleService) {
        this.repository = repository;
        this.resourceBundleService = resourceBundleService;
    }


    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = getEntity(dto);
        entity.setCreatedDate(LocalDateTime.now());


        repository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }


    public ArticleTypeEntity getEntity(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        return entity;
    }

    public ArticleTypeDTO update(Integer id, ArticleTypeDTO dto,Language language) {

        ArticleTypeEntity byId = getById(id,language);


        ArticleTypeEntity entity = getEntity(dto);
        entity.setId(byId.getId());
        entity.setCreatedDate(byId.getCreatedDate());

        repository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean deleteById(Integer id,Language language) {
        getById(id,language);
        repository.deleteById(id);
        return true;

    }

    public Page<ArticleTypeDTO> getList(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ArticleTypeEntity> pageObj = repository.findAll(pageable);

        List<ArticleTypeEntity> content = pageObj.getContent();
        List<ArticleTypeDTO> dtoList = new ArrayList<>();
        content.forEach(entity -> dtoList.add(getFullDTO(entity)));

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    public List<ArticleTypeShortDTO> getByLang(String lang) {

        List<IArticleTypeMapper> mapperList = new ArrayList<>();

        LangUtil.checkLang(lang);

        switch (lang) {
            case "en" -> mapperList = repository.getByLangEn();
            case "uz" -> mapperList = repository.getByLangUz();
            case "ru" -> mapperList = repository.getByLangRu();
        }

        return getDTOList(mapperList);

    }

    private List<ArticleTypeShortDTO> getDTOList(List<IArticleTypeMapper> mapperList) {

        List<ArticleTypeShortDTO> dtoList = new ArrayList<>();

        for (IArticleTypeMapper mapper : mapperList) {
            ArticleTypeShortDTO dto = new ArticleTypeShortDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setKey(mapper.getAKey());
            dtoList.add(dto);

        }
        return dtoList;
    }


    public ArticleTypeEntity getById(Integer id, Language language) {
        Optional<ArticleTypeEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new ArticleTypeNotFoundException(resourceBundleService.getMessage("not.found",language,"ArticleType" ));
        }
        return optional.get();
    }


    public ArticleTypeDTO getFullDTO(ArticleTypeEntity entity) {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());

        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }
}