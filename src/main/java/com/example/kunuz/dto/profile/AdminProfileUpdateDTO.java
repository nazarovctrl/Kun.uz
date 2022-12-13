package com.example.kunuz.dto.profile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AdminProfileUpdateDTO {

    @Min(value = 1, message = "Id required")
    private Integer id;

    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Surname required")
    private String surname;

    @Size(min = 8, message = "Password required")
    private String password;
}
