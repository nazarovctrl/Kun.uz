package com.example.kunuz.service;

import com.example.kunuz.dto.auth.AdminRegistrationDTO;
import com.example.kunuz.dto.profile.*;
import com.example.kunuz.entity.EmailHistoryEntity;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.enums.Language;
import com.example.kunuz.enums.ProfileStatus;
import com.example.kunuz.exp.EmailAlreadyExistsException;
import com.example.kunuz.exp.ItemNotFoundException;
import com.example.kunuz.exp.ProfileNotFoundException;
import com.example.kunuz.repository.ProfileRepository;
import com.example.kunuz.repository.custom.ProfileCustomRepository;
import com.example.kunuz.util.JwtUtil;
import com.example.kunuz.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProfileService {
    private final ProfileRepository repository;
    private final EmailHistoryService emailHistoryService;
    private final MailService mailService;


    private final ProfileCustomRepository customRepository;
    private final ResourceBundleService resourceBundleService;

    public ProfileService(ProfileRepository repository, EmailHistoryService emailHistoryService, MailService mailService, ProfileCustomRepository customRepository, ResourceBundleService resourceBundleService) {
        this.repository = repository;
        this.emailHistoryService = emailHistoryService;
        this.mailService = mailService;
        this.customRepository = customRepository;
        this.resourceBundleService = resourceBundleService;
    }

    public ProfileEntity get(Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            log.warn("Profile not found id = {}", id);
            throw new ItemNotFoundException(resourceBundleService.getMessage("item.not.found", Language.RU, id));
        });
    }

    public ProfileResponseDTO create(AdminRegistrationDTO dto) {

        checkEmail(dto.getEmail());

        ProfileEntity entity = new ProfileEntity();
        entity.setRole(dto.getRole());

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5.md5(dto.getPassword()));

        entity.setStatus(ProfileStatus.NOT_ACTIVE);


        repository.save(entity);

        Thread thread = new Thread() {
            @Override
            public synchronized void start() {
                String sb = "Salom qalaysan \n" +
                        "Bu test message" +
                        "Click the link : http://localhost:8080/auth/verification/email/" +
                        JwtUtil.encode(entity.getEmail(),entity.getRole());
                mailService.sendEmail(dto.getEmail(), "Complete Registration", sb);

                EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();
                emailHistoryEntity.setEmail(dto.getEmail());
                emailHistoryEntity.setMessage(sb);
                emailHistoryEntity.setCreatedDate(LocalDateTime.now());
                emailHistoryService.create(emailHistoryEntity);
            }
        };
        thread.start();

        return getDTO(entity);
    }

    private void checkEmail(String email) {

        Optional<ProfileEntity> exists = repository.findByEmail(email);
        if (exists.isPresent()) {
            ProfileEntity entity = exists.get();
            if (entity.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
                repository.delete(entity);
            } else {
                throw new EmailAlreadyExistsException("Email already exists");
            }
        }
    }


    private ProfileResponseDTO getDTO(ProfileEntity entity) {

        return new ProfileResponseDTO(entity.getId(),
                entity.getName(), entity.getSurname(),
                entity.getStatus(), entity.getRole(), entity.getVisible(), entity.getCreatedDate());
    }


    public ProfileResponseDTO updateUser(UserProfileUpdateDTO dto, Integer id) {


        ProfileEntity entity = getById(id);

        entity.setSurname(dto.getSurname());
        entity.setName(dto.getName());
        entity.setPassword(MD5.md5(dto.getPassword()));

        repository.save(entity);
        ProfileResponseDTO profileDTO = new ProfileResponseDTO();
        profileDTO.setName(dto.getName());
        profileDTO.setSurname(dto.getSurname());

        return profileDTO;

    }

    public ProfileResponseDTO updateAdmin(AdminProfileUpdateDTO dto) {

        ProfileEntity entity = getById(dto.getId());

        entity.setSurname(dto.getSurname());
        entity.setName(dto.getName());
        entity.setPassword(MD5.md5(dto.getPassword()));

        repository.save(entity);
        ProfileResponseDTO profileDTO = new ProfileResponseDTO();
        profileDTO.setName(dto.getName());
        profileDTO.setSurname(dto.getSurname());

        return profileDTO;

    }

    public Page<ProfileResponseDTO> getList(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ProfileEntity> pageObj = repository.findAll(pageable);

        List<ProfileEntity> content = pageObj.getContent();

        List<ProfileResponseDTO> dtoList = new ArrayList<>();

        for (ProfileEntity entity : content) {
            dtoList.add(getDTO(entity));
        }

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }

    public Boolean deleteById(Integer id) {

        repository.deleteById(id);

        return true;
    }


    public ProfileEntity getById(Integer id) {
        Optional<ProfileEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new ProfileNotFoundException("Bunday profile mavjud emas");
        }
        return optional.get();
    }

    public ProfileResponseDTO getFullDTO(ProfileEntity entity) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(entity.getId());
        dto.setRole(entity.getRole());
        dto.setVisible(entity.getVisible());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setSurname(entity.getSurname());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());

        return dto;
    }

    public List<ProfileDTO> filter(ProfileFilterDTO filterDTO, int page, int size) {
        Page<ProfileEntity> list = customRepository.filter(filterDTO, page, size);

        for (ProfileEntity entity : list) {
            System.out.println(entity.getName());
        }
        return null;
    }

}
