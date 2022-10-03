package com.developer.wiki.question.query.application;

import com.developer.wiki.question.command.domain.Category;
import com.developer.wiki.question.command.domain.EntityNotFoundException;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import com.developer.wiki.question.util.QuestionConverter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionDetailService {

  private final QuestionRepository questionRepository;

  public DetailQuestionResponse findDetail(Long questionId, Category category) {
    Question question = questionRepository.findDetail(questionId)
        .orElseThrow(EntityNotFoundException::new);
    List<String> categories = Objects.isNull(category) ? getCategoryToString(question.getCategory())
        : getCategoryToString(category);
    Long prevId = questionRepository.findPrevIdById(questionId, categories).orElse(null);
    Long nextId = questionRepository.findNextIdById(questionId, categories).orElse(null);
    question.addViewCount();
    return QuestionConverter.ofDetail(question, prevId, nextId);
  }

  private List<String> getCategoryToString(Category category) {
    return getCategory(category).stream().map(c -> c.name()).collect(Collectors.toList());
  }

  private List<Category> getCategory(Category category) {
    if (category.equals(Category.FE_ALL) || category.equals(Category.BE_ALL)) {
      return category.equals(Category.FE_ALL) ? Category.frontendAll() : Category.backendAll();
    }
    return List.of(category);
  }
}
