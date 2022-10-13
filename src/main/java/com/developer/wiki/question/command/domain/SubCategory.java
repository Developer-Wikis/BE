package com.developer.wiki.question.command.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public enum SubCategory {
    all("all"), basic("basic"), css("css"), html("html"), javascript("javascript"), react(
            "react"), database("database"), design_pattern("design_pattern"), security_network(
            "network/security"), os("os"), data_structure_algorithm(
            "data_structure/algorithm"), infra_engineering("infra/engineering"), java("java"), spring(
            "spring");

    private final String category;

    public List<String> allOrOneSubCategory(MainCategory mainCategory) {
        if (!this.equals(SubCategory.all)) {
            return List.of(this).stream().map(c -> c.name()).collect(Collectors.toList());
        }
        List<SubCategory> subCategories = mainCategory.equals(MainCategory.fe) ? frontendAll() : backendAll();
        return subCategories.stream().map(c -> c.name()).collect(Collectors.toList());
    }

    public List<SubCategory> frontendAll() {
        return List.of(SubCategory.basic, SubCategory.css, SubCategory.javascript,
                SubCategory.react, SubCategory.database, SubCategory.design_pattern,
                SubCategory.security_network, SubCategory.os,SubCategory.data_structure_algorithm);
    }

    public List<SubCategory> backendAll() {
        return List.of(SubCategory.basic, SubCategory.java, SubCategory.spring,
                SubCategory.database, SubCategory.os, SubCategory.infra_engineering,
                SubCategory.design_pattern, SubCategory.security_network,
                SubCategory.data_structure_algorithm);
    }

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

    public static SubCategory ofForQuery(String category) {
        if (Objects.isNull(category)) {
            return null;
        }
        for (SubCategory c : SubCategory.values()) {
            if (c.name().equals(category)) {
                return c;
            }
        }
        throw new NotMatchCategoryException();
    }

    public String getCategory() {
        return category;
    }

}
