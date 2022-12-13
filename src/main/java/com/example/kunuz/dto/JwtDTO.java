package com.example.kunuz.dto;

import com.example.kunuz.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private Integer id;
    private ProfileRole role;

    public JwtDTO(Integer id, ProfileRole role) {
        this.id = id;
        this.role = role;
    }
}
