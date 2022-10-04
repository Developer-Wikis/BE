package com.developer.wiki.question.command.domain;

import com.developer.wiki.question.util.PasswordEncrypter;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuestionTest {

  @Autowired
  QuestionRepository questionRepository;

  @Test
  void question_create() {
    Question question = new Question("title", "nickname", PasswordEncrypter.encrypt("234"),
        Category.CSS, List.of("sdf", "SDfdf"));
    Question savedQuestion = questionRepository.save(question);
    Assertions.assertThat(savedQuestion.getId()).isNotNull();
  }
}