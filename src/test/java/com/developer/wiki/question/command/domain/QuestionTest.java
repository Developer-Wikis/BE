package com.developer.wiki.question.command.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class QuestionTest {

  @Autowired
  QuestionRepository questionRepository;

  @Test
  void question_create() {
    Question question = new Question("title", MainCategory.be, SubCategory.css);
    Question savedQuestion = questionRepository.save(question);
    Assertions.assertThat(savedQuestion.getId()).isNotNull();
  }
}