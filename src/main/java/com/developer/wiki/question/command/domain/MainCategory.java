package com.developer.wiki.question.command.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public enum MainCategory {
  fe, be, all;

  public static MainCategory of(String category) {
    if (Objects.isNull(category)) {
      return null;
    }
    for (MainCategory c : MainCategory.values()) {
      if (c.name().equals(category.toLowerCase())) {
        return c;
      }
    }
    throw new NotMatchCategoryException();
  }

}
