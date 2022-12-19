package com.example.kunuz.repository;

import com.example.kunuz.entity.ProfileEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>, PagingAndSortingRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByEmail(String mail);
    Optional<ProfileEntity> findByEmailAndPassword(String phone,String password);


}
