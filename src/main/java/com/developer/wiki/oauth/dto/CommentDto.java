package com.developer.wiki.oauth.dto;

import com.developer.wiki.question.command.domain.MainCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.jandex.Main;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private String title;
    private MainCategory mainCategory;
}
