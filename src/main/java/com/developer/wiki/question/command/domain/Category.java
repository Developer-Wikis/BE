package com.developer.wiki.question.command.domain;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {
  FE_ALL("FE ALL"), BE_ALL("BE ALL"), FE기본("FE 기본"), BE기본("BE 기본"), CSS("CSS"), HTML(
      "HTML"), JAVASCRIPT("JAVASCRIPT"), REACT("REACT"), JAVA("JAVA"), SPRING("SPRING"), 네트워크(
      "네트워크"), 데이터베이스("데이터베이스"), 디자인패턴("디자인패턴"), 보안("보안"), 운영체제("운영체제"), 자료구조_알고리즘(
      "자료구조/알고리즘"), 인프라_엔지니어링("인프라/엔지니어링");

  private final String category;

  public static List<Category> frontendAll() {
    return List.of(Category.FE기본, Category.CSS, Category.HTML,  Category.JAVASCRIPT,
        Category.REACT, Category.네트워크, Category.디자인패턴, Category.보안, Category.자료구조_알고리즘);
  }

  public static Category of(String category) {
    for (Category c : Category.values()) {
      if (c.category.equals(category)) {
        return c;
      }
    }
    return null;
  }

  public String getCategory() {
    return category;
  }

  public static List<Category> backendAll() {
    return List.of(Category.BE기본, Category.JAVA, Category.SPRING, Category.데이터베이스, Category.운영체제,
        Category.인프라_엔지니어링, Category.네트워크, Category.디자인패턴, Category.보안, Category.자료구조_알고리즘);
  }
}
