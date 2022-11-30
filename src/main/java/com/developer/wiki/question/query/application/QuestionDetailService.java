package com.developer.wiki.question.query.application;

import com.developer.wiki.bookmark.BookmarkRepository;
import com.developer.wiki.oauth.User;
import com.developer.wiki.question.command.domain.*;
import com.developer.wiki.question.util.QuestionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionDetailService {

  private final QuestionRepository questionRepository;
  private final BookmarkRepository bookmarkRepository;

  public DetailQuestionResponse findDetail(Long questionId, MainCategory mainCategory,
      SubCategory subCategory, User currentUser) {
    Question question = questionRepository.findDetail(questionId)
        .orElseThrow(EntityNotFoundException::new);
    List<String> subCategories = subCategory.allOrOneSubCategory(mainCategory);
    Long prevId = questionRepository.findPrevIdById(questionId, mainCategory.name(), subCategories)
        .orElse(null);
    Long nextId = questionRepository.findNextIdById(questionId, mainCategory.name(), subCategories)
        .orElse(null);
    question.addViewCount();
    return QuestionConverter.ofDetail(question, question.getTailQuestions(), prevId, nextId,
        isBookmarkedQuestion(currentUser, question));
  }

  private Boolean isBookmarkedQuestion(User currentUser, Question question) {
    if (!Objects.isNull(currentUser)) {
      return bookmarkRepository.existsByUserIdAndQuestion(currentUser.getId(), question);
    }
    return Boolean.FALSE;
  }
}
