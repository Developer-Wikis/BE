package com.developer.wiki.question.command.application.dto;

import com.developer.wiki.question.command.domain.Category;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionRequest {

  private String nickname;
  private String password;
  private String title;
  private Category category;
  @Builder.Default
  private List<String> additionQuestions = new ArrayList<>();
}
