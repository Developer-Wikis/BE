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

  @NotBlank(message = "닉네임은 null일 수 없습니다.")
  private String nickname;
  @NotBlank(message = "제목은 null일 수 없습니다.")
  private String title;
  @NotBlank(message = "카테고리는 null일 수 없습니다.")
  private String category;
  @Builder.Default
  private List<String> additionQuestions = new ArrayList<>();
}
