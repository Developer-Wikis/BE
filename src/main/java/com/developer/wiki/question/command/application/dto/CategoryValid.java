package com.developer.wiki.question.command.application.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CategoryValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryValid {
  String message() default "Categery는 null일 수 없습니다.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}