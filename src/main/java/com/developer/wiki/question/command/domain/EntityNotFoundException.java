package com.developer.wiki.question.command.domain;

import com.developer.wiki.common.exception.NotFoundException;

public class EntityNotFoundException extends NotFoundException {

  private static final String NOT_FOUND = "엔티티를 찾을 수 없습니다..";

  public EntityNotFoundException() {
    super(NOT_FOUND);
  }
}
