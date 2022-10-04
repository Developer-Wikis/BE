package com.developer.wiki.question.command.domain;

import com.developer.wiki.question.util.PasswordEncrypter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentTest {
  @Autowired
  CommentRepository commentRepository;

  @Test
  void comment_create() {
    Comment comment = new Comment("nickname", PasswordEncrypter.encrypt("sdf"), "content", null);
    Comment savedComment = commentRepository.save(comment);
    Assertions.assertThat(savedComment.getId()).isNotNull();
  }
}