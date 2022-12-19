package com.example.kunuz.dto.comment;


import com.example.kunuz.dto.article.ArticleShortDTO;
import com.example.kunuz.dto.profile.ProfileShortDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDTO {

    private Integer id;
    private String content;
    private String articleId;
    private Integer replyId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private ProfileShortDTO profileShortDTO;
    private ArticleShortDTO articleShortDTO;

}
