package com.developer.wiki.question.command.domain;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public enum Category {
  fe_all("fe all"), fe_basic("fe basic"), fe_css("fe css"), fe_html("fe html"), fe_javascript(
      "fe javascript"), fe_react("fe react"), fe_database("fe database"), fe_design_pattern(
      "fe design_pattern"), fe_security_network("fe network/security"), fe_os(
      "fe os"), fe_data_structure_algorithm("fe data_structure/algorithm"), fe_infra_engineering(
      "fe infra/engineering"), be_all("be all"), be_java("be java"), be_basic(
      "be basic"), be_spring("be spring"), be_database("be database"), be_design_pattern(
      "be design_pattern"), be_security_network("be network/security"), be_os(
      "be os"), be_data_structure_algorithm("be data_structure/algorithm"), be_infra_engineering(
      "be infra/engineering");

  private final String category;

  public static List<Category> frontendAll() {
    return List.of(Category.fe_basic, Category.fe_css, Category.fe_html, Category.fe_javascript,
        Category.fe_react, Category.fe_design_pattern, Category.fe_security_network,
        Category.fe_data_structure_algorithm);
  }

  public static List<Category> backendAll() {
    return List.of(Category.be_basic, Category.be_java, Category.be_spring, Category.be_database,
        Category.be_os, Category.be_infra_engineering, Category.be_design_pattern,
        Category.be_security_network, Category.be_data_structure_algorithm);
  }

  public static Category of(String category) {
    if (Objects.isNull(category)) {
      return null;
    }
    for (Category c : Category.values()) {
      if (c.category.equals(category.toLowerCase())) {
        return c;
      }
    }
    throw new NotMatchCategoryException();
  }

  public String getCategory() {
    return category;
  }

}
