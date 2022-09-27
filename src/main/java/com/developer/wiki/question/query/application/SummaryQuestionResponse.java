package com.developer.wiki.question.query.application;

import com.developer.wiki.question.command.domain.Category;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryQuestionResponse {

  private Long id;
  private String title;
  private String nickname;
  private Category category;
  private Long viewCount;
  private Long commentCount;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime createdAt;
}