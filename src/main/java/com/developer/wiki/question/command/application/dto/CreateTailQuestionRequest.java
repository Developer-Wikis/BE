package com.developer.wiki.question.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTailQuestionRequest {

  @NotBlank(message = "질문은 null일 수 없습니다.")
  private String tailQuestion;
}
