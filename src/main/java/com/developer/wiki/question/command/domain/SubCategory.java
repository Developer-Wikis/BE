package com.developer.wiki.question.command.domain;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public enum SubCategory {
  all("all"), basic("basic"), css("css"), html("html"), javascript("javascript"), react(
      "fe react"), database("database"), design_pattern("design_pattern"), security_network(
      "network/security"), os("os"), data_structure_algorithm(
      "data_structure/algorithm"), infra_engineering("infra/engineering"), java("java"), spring(
      "spring");

  private final String category;

//  public static List<SubCategory> frontendAll() {
//    return List.of(SubCategory.fe_basic, SubCategory.fe_css, SubCategory.fe_html,
//        SubCategory.fe_javascript, SubCategory.fe_react, SubCategory.fe_design_pattern,
//        SubCategory.fe_security_network, SubCategory.fe_data_structure_algorithm);
//  }
//
//  public static List<SubCategory> backendAll() {
//    return List.of(SubCategory.be_basic, SubCategory.be_java, SubCategory.be_spring,
//        SubCategory.be_database, SubCategory.be_os, SubCategory.be_infra_engineering,
//        SubCategory.be_design_pattern, SubCategory.be_security_network,
//        SubCategory.be_data_structure_algorithm);
//  }

  public static SubCategory of(String category) {
    if (Objects.isNull(category)) {
      return null;
    }
    for (SubCategory c : SubCategory.values()) {
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
