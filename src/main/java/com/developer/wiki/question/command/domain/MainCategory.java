package com.developer.wiki.question.command.domain;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public enum MainCategory {
  fe, be;

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
