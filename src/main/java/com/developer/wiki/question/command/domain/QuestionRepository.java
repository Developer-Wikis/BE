package com.developer.wiki.question.command.domain;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {
  Slice<Question> findSliceBy(Pageable pageable);

  @Query("SELECT distinct q FROM Question q join fetch q.comments")
  Optional<Question> findDetail(Long questionId);
}
