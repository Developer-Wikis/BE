package com.developer.wiki.question.command.domain;

import com.developer.wiki.question.query.application.DetailQuestionResponse;
import com.developer.wiki.question.query.application.QuestionData;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  @Query("SELECT distinct q FROM Question q left join fetch q.comments left join fetch q.additionQuestions where q.id = :questionId")
  Optional<Question> findDetail(Long questionId);

  @Query(value =
      "select query.prevId "
          + "from (select id,LAG(id,1) over(partition by q.category order by created_at) as prevId from question as q order by q.created_at) as query "
          + "where query.id = :questionId", nativeQuery = true)
  Optional<Long> findPrevIdById(Long questionId);

  @Query(value =
      "select query.nextId "
          + "from (select id,LEAD(id,1) over(partition by q.category order by created_at) as nextId from question as q order by q.created_at) as query "
          + "where query.id = :questionId", nativeQuery = true)
  Optional<Long> findNextIdById(Long questionId);

}
