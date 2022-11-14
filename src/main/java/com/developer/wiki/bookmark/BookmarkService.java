package com.developer.wiki.bookmark;

import com.developer.wiki.common.exception.NotFoundException;
import com.developer.wiki.oauth.UserRepository;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

  private final QuestionRepository questionRepository;
  private final UserRepository userRepository;
  private final BookmarkRepository bookmarkRepository;

  public void toggle(Long questionId, Long userId) {
    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 ID입니다."));
    bookmarkRepository.findByUserIdAndQuestion(userId, question).ifPresentOrElse(bookmark -> {
      unBookmark(bookmark);
    }, () -> {
      bookmark(userId, question);
    });
  }

  private void bookmark(Long userId, Question question) {
    Bookmark bookmark = new Bookmark(userId, question);
    bookmarkRepository.save(bookmark);
  }

  private void unBookmark(Bookmark bookmark) {
    bookmarkRepository.delete(bookmark);
  }
}
