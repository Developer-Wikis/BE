package com.developer.wiki.question.command.application.dto;

import com.developer.wiki.common.exception.BadRequestException;

public class CategoryIsNullException extends BadRequestException {

  private static final String IS_NULL = "카테고리는 null이 될 수 없습니다.";

  public CategoryIsNullException() {
    super(IS_NULL);
  }
}
