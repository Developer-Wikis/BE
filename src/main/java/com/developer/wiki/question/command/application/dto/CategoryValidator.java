package com.developer.wiki.question.command.application.dto;


import com.developer.wiki.question.command.domain.Category;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<CategoryValid, Category> {

  @Override
  public boolean isValid(Category category, ConstraintValidatorContext context) {
    if (Objects.isNull(category)) {
      throw new CategoryIsNullException();
    }
    return true;
  }
}