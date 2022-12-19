package com.example.kunuz.util;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.dto.profile.ProfileResponseDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.exp.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestUtil {

    public static Integer getProfileId(HttpServletRequest request, ProfileRole profileRole) {

        Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole.valueOf(request.getAttribute("role").toString()));

        if (!role.equals(profileRole)) {
            throw new ForbiddenException("Method Not Allowed");
        }
        return id;

    }


    public static Integer getProfileId(HttpServletRequest request) {

        return (Integer) request.getAttribute("id");

    }

    public static ProfileResponseDTO getProfile(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("id");
        ProfileRole role = (ProfileRole.valueOf(request.getAttribute("role").toString()));

        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(id);
        dto.setRole(role);
        return dto;

    }
}
