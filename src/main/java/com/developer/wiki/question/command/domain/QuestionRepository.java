package com.developer.wiki.question.command.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{

  @Query("SELECT distinct q FROM Question q left join fetch q.comments where q.id = :questionId")
  Optional<Question> findDetail(Long questionId);
}
