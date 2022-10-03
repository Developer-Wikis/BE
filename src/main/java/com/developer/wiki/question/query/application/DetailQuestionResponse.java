package com.developer.wiki.question.query.application;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailQuestionResponse {

  private Long id;
  private String title;
  private String nickname;
  private String category;
  private Set<String> additionQuestions;
  private Long viewCount;
  private Long commentCount;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime createdAt;
  private Long prevId;
  private Long nextId;
}
