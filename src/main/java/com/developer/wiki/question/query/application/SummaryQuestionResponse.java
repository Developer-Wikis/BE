package com.developer.wiki.question.query.application;

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
  private String category;
  private Long viewCount;
  private Long commentCount;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime createdAt;
}
