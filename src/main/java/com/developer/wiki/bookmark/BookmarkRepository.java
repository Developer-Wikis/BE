package com.developer.wiki.bookmark;

import com.developer.wiki.question.command.domain.Question;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

  Optional<Bookmark> findByUserIdAndQuestion(Long userId, Question question);

  Boolean existsByUserIdAndQuestion(Long userId, Question question);
}
