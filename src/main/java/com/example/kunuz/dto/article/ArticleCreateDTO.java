package com.example.kunuz.dto.article;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArticleCreateDTO {

    @NotBlank(message = "Title required Mazgi")
    private String title;

    @Size(min = 10, max = 140, message = "Description required")
    private String description;

    @Size(min = 20, message = "Content required")
    private String content;

    @NotBlank(message = "ImageId required")
    private String imageId;

    @Min(value = 1, message = "RegionId required")
    private Integer regionId;

    @Min(value = 1, message = "ArticleTypeId required")
    private Integer articleTypeId;


}
