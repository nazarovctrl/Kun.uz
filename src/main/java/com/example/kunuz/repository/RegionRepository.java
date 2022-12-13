package com.example.kunuz.repository;

import com.example.kunuz.entity.RegionEntity;
import com.example.kunuz.mapper.IArticleTypeMapper;
import com.example.kunuz.mapper.IRegionMapper;
import jdk.jfr.Registered;
import org.aspectj.weaver.reflect.IReflectionWorld;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@Registered
public interface RegionRepository extends CrudRepository<RegionEntity, Integer>, PagingAndSortingRepository<RegionEntity, Integer> {
    @Query(value = "SELECT a.id as id, a.name_uz as name, a.key as aKey from region a", nativeQuery = true)
    List<IRegionMapper> getByLangUz();

    @Query(value = "SELECT a.id as id, a.name_ru as name, a.key as key from region a", nativeQuery = true)
    List<IRegionMapper> getByLangRu();

    @Query(value = "SELECT a.id as id, a.name_en as name, a.key as key from region a", nativeQuery = true)
    List<IRegionMapper> getByLangEn();

    RegionEntity findByKey(String key);
}
