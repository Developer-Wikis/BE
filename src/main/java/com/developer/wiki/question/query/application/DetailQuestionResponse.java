package com.developer.wiki.question.query.application;

import com.developer.wiki.question.command.domain.Category;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
  private Category category;
  @Builder.Default
  private List<String> additionQuestions = new ArrayList<>();
  private Long viewCount;
  private Long commentCount;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime createdAt;
}
