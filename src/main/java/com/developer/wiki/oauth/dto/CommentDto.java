package com.developer.wiki.oauth.dto;

import com.developer.wiki.question.command.domain.MainCategory;
import com.developer.wiki.question.command.domain.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private Long questionId;
    private String title;
    private MainCategory mainCategory;
    private SubCategory subCategory;
}
