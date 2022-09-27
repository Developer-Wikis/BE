package com.developer.wiki.question.command.domain;

import com.developer.wiki.common.exception.UnAuthorizedException;

public class NotMatchPasswordException extends UnAuthorizedException {

  private static final String NOT_MATCH_PASSWORD = "비밀번호가 맞지 않습니다.";

  public NotMatchPasswordException() {
    super(NOT_MATCH_PASSWORD);
  }
}
