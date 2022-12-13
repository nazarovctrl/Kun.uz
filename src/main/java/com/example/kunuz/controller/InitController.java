package com.example.kunuz.controller;


import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.enums.ProfileStatus;
import com.example.kunuz.repository.ProfileRepository;
import com.example.kunuz.util.MD5;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/init")
public class InitController {

    private final ProfileRepository repository;

    public InitController(ProfileRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/admin")
    public String initDmin() {
        ProfileEntity exists = repository.findByEmail("admin@gmail.com");

        if (exists != null) {
            return "Already  exists";
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName("Admin");
        entity.setSurname("Adminjon");
        entity.setEmail("admin@gmail.com");
        entity.setRole(ProfileRole.ADMIN);
        entity.setPassword(MD5.md5("200622az"));
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(ProfileStatus.ACTIVE);

        repository.save(entity);
        return "created";

    }
}
