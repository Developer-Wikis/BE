package com.developer.wiki.question.command.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByQuestion_Id(Long questionId);

  Long countByUserId(Long uerId);


  @EntityGraph(attributePaths = {"question"})
  List<Comment> findByUserId(Long userId);
}
