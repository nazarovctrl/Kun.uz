package com.example.kunuz.dto.article;


import com.example.kunuz.dto.profile.ProfileResponseDTO;
import com.example.kunuz.dto.region.RegionDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleResponseDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private Integer viewCount;
    private String imageId;
    private Integer regionId;
    private Integer articleTypeId;

    private Integer moderatorId;

    private RegionDTO regionDTO;
    private ArticleTypeDTO articleTypeDTO;
    private ProfileResponseDTO moderatorDTO;
    private ProfileResponseDTO publisherDTO;


    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;

}
