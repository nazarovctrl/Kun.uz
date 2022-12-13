package com.example.kunuz.dto.region;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO {
    private Integer id;

    @NotBlank(message = "Key required")
    private String key;

    @NotBlank(message = "NameUz required")
    private String nameUz;

    @NotBlank(message = "NameRu required")
    private String nameRu;

    @NotBlank(message = "NameEn required")
    private String nameEn;

    private LocalDateTime createdDate;
}
