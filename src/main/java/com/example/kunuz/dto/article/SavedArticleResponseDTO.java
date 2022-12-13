package com.example.kunuz.dto.article;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SavedArticleResponseDTO {
    //                  (id,article(id,title,description,image(id,url)))
    private Integer id;
    private ArticleShortInfoDTO articleShortInfoDTO;

}
