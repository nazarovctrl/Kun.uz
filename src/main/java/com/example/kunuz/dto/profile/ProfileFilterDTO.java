package com.example.kunuz.dto.profile;

import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileFilterDTO {

    private Integer id;
    private String name;
    private String surname;
    private ProfileStatus status;
    private ProfileRole role;
    private String email;
    private LocalDate fromDate;
    private LocalDate toDate;

}
