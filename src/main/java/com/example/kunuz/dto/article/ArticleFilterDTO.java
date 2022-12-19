package com.example.kunuz.dto.article;

import com.example.kunuz.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ArticleFilterDTO {

    private String title;
    private String regionId;
    private Integer articleTypeId;
    private Integer publisherId;
    private Integer moderatorId;
    private ArticleStatus status;
    private LocalDate fromDate;
    private LocalDate toDate;
}
