package com.developer.wiki.question.query.application;

import com.developer.wiki.bookmark.BookmarkRepository;
import com.developer.wiki.oauth.User;
import com.developer.wiki.question.command.domain.EntityNotFoundException;
import com.developer.wiki.question.command.domain.MainCategory;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import com.developer.wiki.question.command.domain.SubCategory;
import com.developer.wiki.question.util.QuestionConverter;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    return QuestionConverter.ofDetail(question, question.getTailQuestions(), prevId, nextId,
        isBookmarkedQuestion(currentUser, question));
  }

  public void addViewCount(Long questionId) {
    Question question = questionRepository.findDetail(questionId)
        .orElseThrow(EntityNotFoundException::new);
    question.addViewCount();
    questionRepository.save(question);
  }

  private Boolean isBookmarkedQuestion(User currentUser, Question question) {
    if (Objects.nonNull(currentUser)) {
      return bookmarkRepository.existsByUserIdAndQuestion_Id(currentUser.getId(), question.getId());
    }
    return Boolean.FALSE;
  }
}
