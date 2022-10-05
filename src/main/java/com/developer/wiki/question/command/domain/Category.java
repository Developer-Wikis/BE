package com.developer.wiki.question.command.domain;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public enum Category {
    fe_all, be_all, fe_basic, be_basic, css, html, javascript, react, java,
    spring, network, database, design_pattern, security, os, data_structure_algorithm,
    infra_engineering;

//  private final String category;

  public static List<Category> frontendAll() {
    return List.of(Category.fe_basic, Category.css, Category.html, Category.javascript, Category.react,
        Category.network, Category.design_pattern, Category.security, Category.data_structure_algorithm);
  }

  public static Category of(String category) {
    if (Objects.isNull(category)) {
      return null;
    }
    for (Category c : Category.values()) {
      if (c.toString().equals(category)) {
        return c;
      }
    }
    log.info("이건 테스트 {}",category);
    throw new NotMatchCategoryException();
  }

//  public String getCategory() {
//    return category;
//  }

  public static List<Category> backendAll() {
    return List.of(Category.be_basic, Category.java, Category.spring, Category.database, Category.os,
        Category.infra_engineering, Category.network, Category.design_pattern, Category.security, Category.data_structure_algorithm);
  }
}
