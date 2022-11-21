package com.developer.wiki.bookmark;

import com.developer.wiki.common.exception.NotFoundException;
import com.developer.wiki.oauth.UserRepository;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import java.util.concurrent.atomic.AtomicBoolean;
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

  public Boolean toggle(Long questionId, Long userId) {
    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 ID입니다."));
    AtomicBoolean isBookmarked = new AtomicBoolean();
    bookmarkRepository.findByUserIdAndQuestion(userId, question).ifPresentOrElse(bookmark -> {
      unBookmark(bookmark);
      isBookmarked.set(false);
    }, () -> {
      bookmark(userId, question);
      isBookmarked.set(true);
    });
    return isBookmarked.get();
  }

  public Boolean getBookmarked(Long questionId, Long userId) {
    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 ID입니다."));
    AtomicBoolean isBookmarked = new AtomicBoolean();
    bookmarkRepository.findByUserIdAndQuestion(userId, question).ifPresentOrElse(bookmark -> {
      isBookmarked.set(true);
    }, () -> {
      isBookmarked.set(false);
    });
    return isBookmarked.get();
  }

  private void bookmark(Long userId, Question question) {
    Bookmark bookmark = new Bookmark(userId, question);
    bookmarkRepository.save(bookmark);
  }

  private void unBookmark(Bookmark bookmark) {
    bookmarkRepository.delete(bookmark);
  }

}
