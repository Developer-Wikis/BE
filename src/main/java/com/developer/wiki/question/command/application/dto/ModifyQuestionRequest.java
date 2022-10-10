package com.developer.wiki.question.command.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyQuestionRequest {

  private String title;
  private String category;
  private List<String> additionQuestions;
}
