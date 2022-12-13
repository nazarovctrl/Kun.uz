package com.example.kunuz.service;

import com.example.kunuz.dto.region.RegionDTO;

import com.example.kunuz.dto.region.RegionShortDTO;
import com.example.kunuz.entity.RegionEntity;
import com.example.kunuz.exp.RegionNotFoundException;
import com.example.kunuz.mapper.IRegionMapper;
import com.example.kunuz.repository.RegionRepository;
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
public class RegionService {

    private final RegionRepository repository;

    public RegionService(RegionRepository repository) {
        this.repository = repository;
    }

    public RegionDTO create(RegionDTO dto) {


        RegionEntity entity = new RegionEntity();
        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setCreatedDate(LocalDateTime.now());


        repository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public RegionDTO update(Integer id, RegionDTO dto) {


        RegionEntity entity = getById(id);

        entity.setKey(dto.getKey());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        repository.save(entity);
        dto.setId(entity.getId());

        return dto;
    }

    public Boolean deleteById(Integer id) {
        getById(id);

        repository.deleteById(id);
        return true;

    }

    public Page<RegionDTO> getList(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<RegionEntity> pageObj = repository.findAll(pageable);

        List<RegionEntity> content = pageObj.getContent();
        List<RegionDTO> dtoList = new ArrayList<>();

        content.forEach(regionEntity -> dtoList.add(getFullDTO(regionEntity)));
        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    public List<RegionShortDTO> getByLang(String lang) {

        List<IRegionMapper> mapperList = new ArrayList<>();

        LangUtil.checkLang(lang);

        switch (lang) {
            case "en" -> mapperList = repository.getByLangEn();
            case "uz" -> mapperList = repository.getByLangUz();
            case "ru" -> mapperList = repository.getByLangRu();
        }

        return getDTOList(mapperList);
    }


    private List<RegionShortDTO> getDTOList(List<IRegionMapper> mapperList) {

        List<RegionShortDTO> dtoList = new ArrayList<>();

        for (IRegionMapper mapper : mapperList) {
            RegionShortDTO dto = new RegionShortDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setKey(mapper.getAKey());
            dtoList.add(dto);

        }
        return dtoList;
    }


    public RegionEntity getById(Integer id) {
        Optional<RegionEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new RegionNotFoundException("article type not found");
        }
        return optional.get();
    }

    public RegionDTO getFullDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());

        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public RegionEntity getByKey(String key) {
        RegionEntity entity = repository.findByKey(key);
        if (entity == null) {
            throw new RegionNotFoundException("Region Not Found");
        }
        return entity;
    }
}
