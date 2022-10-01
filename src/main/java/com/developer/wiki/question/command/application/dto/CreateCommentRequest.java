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

  @NotBlank(message = "닉네임은 null일 수 없습니다.")
  private String nickname;
  @NotBlank(message = "비밀번호는 null일 수 없습니다.")
  private String password;
  @NotBlank(message = "내용은 null일 수 없습니다.")
  private String content;
}
