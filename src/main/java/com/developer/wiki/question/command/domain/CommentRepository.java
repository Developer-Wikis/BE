package com.developer.wiki.question.command.domain;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByQuestion_Id(Long questionId);


  @EntityGraph(attributePaths = {"question"})
  List<Comment> findByUserId(Long userId);
}
