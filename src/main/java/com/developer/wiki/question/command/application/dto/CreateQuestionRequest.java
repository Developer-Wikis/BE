package com.developer.wiki.question.command.application.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionRequest {

  @NotBlank(message = "제목은 null일 수 없습니다.")
  private String title;
  @NotBlank(message = "메인 카테고리는 null일 수 없습니다.")
  private String mainCategory;
  @NotBlank(message = "서브 카테고리는 null일 수 없습니다.")
  private String subCategory;
  @Builder.Default
  private List<String> tailQuestions = new ArrayList<>();
}
