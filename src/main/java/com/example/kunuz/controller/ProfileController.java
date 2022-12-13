package com.example.kunuz.controller;

import com.example.kunuz.dto.auth.AdminRegistrationDTO;
import com.example.kunuz.dto.profile.ProfileResponseDTO;
import com.example.kunuz.dto.profile.AdminProfileUpdateDTO;
import com.example.kunuz.dto.profile.UserProfileUpdateDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.ProfileService;
import com.example.kunuz.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService service;


    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @PostMapping("/admin/create")
    public ResponseEntity<?> create(@RequestBody AdminRegistrationDTO dto, HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        ProfileResponseDTO result = service.create(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody UserProfileUpdateDTO dto, HttpServletRequest request) {

        Integer pId = HttpRequestUtil.getProfileId(request, ProfileRole.USER);

        ProfileResponseDTO result = service.updateUser(dto, pId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<?> updateAdmin(@RequestBody AdminProfileUpdateDTO dto, HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        ProfileResponseDTO result = service.updateAdmin(dto);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/admin/list")
    public ResponseEntity<?> getList(
            @RequestParam("page") Integer page, @RequestParam("size") Integer size,
            HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        Page<ProfileResponseDTO> result = service.getList(page, size);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request) {

        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);

        Boolean result = service.deleteById(id);
        return ResponseEntity.ok(result);
    }


}
