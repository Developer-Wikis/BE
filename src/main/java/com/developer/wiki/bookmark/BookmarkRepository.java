package com.developer.wiki.bookmark;

import com.developer.wiki.question.command.domain.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

  List<Bookmark> findAllByUserIdAndQuestion(Long userId, Question question);

  boolean existsByUserIdAndQuestion(Long userId, Question question);

  Long countByUserId(Long userId);

  void deleteByUserIdAndQuestion(Long userId, Question question);
}
