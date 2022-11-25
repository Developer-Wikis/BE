package com.developer.wiki.question.command.domain;

import com.developer.wiki.common.exception.BadRequestException;

public class NotMatchCategoryException extends BadRequestException {

  private static final String NOT_MATCH_CATEGORY = "카테고리가 맞지 않습니다.";

  public NotMatchCategoryException() {
    super(NOT_MATCH_CATEGORY);
  }
  public NotMatchCategoryException(String error) {
    super(error);
  }
}
