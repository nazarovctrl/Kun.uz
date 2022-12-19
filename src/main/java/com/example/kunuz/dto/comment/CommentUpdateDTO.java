package com.example.kunuz.dto.comment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateDTO {

    @NotBlank(message = "Content required")
    private String content;

    @Min(value = 1, message = "Comment id required")
    private Integer commentId;

}
