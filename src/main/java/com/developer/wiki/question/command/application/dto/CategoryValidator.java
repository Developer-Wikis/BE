package com.developer.wiki.question.command.application.dto;


import com.developer.wiki.question.command.domain.Category;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryTypeValidator implements ConstraintValidator<CategoryTypeValid, String> {

  @Override
  public boolean isValid(String memberType, ConstraintValidatorContext context) {
    try {
      Category.valueOf(memberType.toUpperCase());
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}