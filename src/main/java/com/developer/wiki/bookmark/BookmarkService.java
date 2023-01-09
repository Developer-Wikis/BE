package com.developer.wiki.bookmark;

import com.developer.wiki.common.exception.NotFoundException;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

  private final QuestionRepository questionRepository;
  private final BookmarkRepository bookmarkRepository;

  public Boolean toggle(Long questionId, Long userId) {
    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 ID입니다."));
    List<Bookmark> bookmarks = bookmarkRepository.findAllByUserIdAndQuestion(userId, question);
    if (bookmarks.isEmpty()) {
      bookmark(userId, question);
      return true;
    } else {
      unBookmark(userId, question);
      return false;
    }
  }

  public Boolean getBookmarked(Long questionId, Long userId) {
    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 ID입니다."));
    List<Bookmark> bookmarks = bookmarkRepository.findAllByUserIdAndQuestion(userId, question);
    return bookmarks.isEmpty() ? false : true;
  }

  private void bookmark(Long userId, Question question) {
    Bookmark bookmark = new Bookmark(userId, question);
    bookmarkRepository.save(bookmark);
  }

  private void unBookmark(Long userId, Question question) {
    bookmarkRepository.deleteByUserIdAndQuestion(userId, question);
  }

  public Long getMyBookMarkSize(Long userId) {
    return bookmarkRepository.countByUserId(userId);
  }
}
