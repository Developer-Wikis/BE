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
public class SummaryCommentResponse {

  private String nickname;
  private String content;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime createdAt;
}
