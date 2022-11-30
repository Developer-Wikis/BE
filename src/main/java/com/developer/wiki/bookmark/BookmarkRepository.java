package com.developer.wiki.bookmark;

import com.developer.wiki.question.command.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

  Optional<Bookmark> findByUserIdAndQuestion(Long userId, Question question);

  boolean existsByUserIdAndQuestion(Long userId, Question question);

  Long countByUserId(Long userId);
}
