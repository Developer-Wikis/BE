package com.developer.wiki.bookmark;

import com.developer.wiki.question.command.domain.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

  List<Bookmark> findAllByUserIdAndQuestion(Long userId, Question question);

  boolean existsByUserIdAndQuestion_Id(Long userId, Long questionId);

  Integer countByUserIdAndQuestion_Id(Long userId, Long questionId);

  List<Bookmark> findAllByUserIdAndQuestion_Id(Long userId, Long questionId);

  Long countByUserId(Long userId);

  void deleteByUserIdAndQuestion(Long userId, Question question);
}
