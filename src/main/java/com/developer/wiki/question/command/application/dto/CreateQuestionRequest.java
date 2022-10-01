package com.developer.wiki.question.command.application.dto;

import com.developer.wiki.question.command.domain.Category;
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
  @NotBlank(message = "비밀번호는 null일 수 없습니다.")
  private String password;
  @NotBlank(message = "제목은 null일 수 없습니다.")
  private String title;
  @CategoryValid
  private Category category;
  @Builder.Default
  private List<String> additionQuestions = new ArrayList<>();
}
