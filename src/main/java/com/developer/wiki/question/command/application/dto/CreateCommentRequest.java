package com.developer.wiki.question.command.application.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {

  private String nickname;
  private String password;
  @NotBlank(message = "내용은 null일 수 없습니다.")
  private String content;
}
