package com.developer.wiki.question.command.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  @Query("SELECT distinct q FROM Question q where q.id = :questionId")
  Optional<Question> findDetail(Long questionId);

  @Query(value = "select query.prevId "
      + "from (select id,LAG(id,1) over(partition by q.sub_category in :subCategory order by created_at) as prevId from question as q where q.is_approved is true and q.main_category =:mainCategory order by q.created_at) as query "
      + "where query.id = :questionId", nativeQuery = true)
  Optional<Long> findPrevIdById(Long questionId, String mainCategory, List<String> subCategory);

  @Query(value = "select query.nextId "
      + "from (select id,LEAD(id,1) over(partition by q.sub_category in :subCategory order by created_at) as nextId from question as q where q.is_approved is true and q.main_category =:mainCategory order by q.created_at) as query "
      + "where query.id = :questionId", nativeQuery = true)
  Optional<Long> findNextIdById(Long questionId, String mainCategory, List<String> subCategory);

  @Query(value = "select query.prevId "
      + "from (select q.id as id,LAG(q.id,1) over(partition by q.sub_category in :subCategory order by created_at) as prevId from question as q join bookmark as b on q.id = b.question_id where q.is_approved is true and q.main_category =:mainCategory and b.user_id =:userId order by q.created_at) as query "
      + "where query.id = :questionId", nativeQuery = true)
  Optional<Long> findPrevIdByIdAndUserId(Long questionId, String mainCategory,
      List<String> subCategory, Long userId);

  @Query(value = "select query.nextId "
      + "from (select q.id as id,LEAD(q.id,1) over(partition by q.sub_category in :subCategory order by created_at) as nextId from question as q right join bookmark as b on q.id = b.question_id where q.is_approved is true and q.main_category =:mainCategory and b.user_id =:userId order by q.created_at) as query "
      + "where query.id = :questionId", nativeQuery = true)
  Optional<Long> findNextIdByIdAndUserId(Long questionId, String mainCategory,
      List<String> subCategory, Long userId);

}
