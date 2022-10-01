package com.developer.wiki.question.command.application.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CategoryTypeValidator.class)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryTypeValid {
  String message() default "MemberType enum value check";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}