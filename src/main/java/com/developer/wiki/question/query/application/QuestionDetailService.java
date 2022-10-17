package com.developer.wiki.question.query.application;

import com.developer.wiki.question.command.domain.EntityNotFoundException;
import com.developer.wiki.question.command.domain.MainCategory;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import com.developer.wiki.question.command.domain.SubCategory;
import com.developer.wiki.question.util.QuestionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionDetailService {

  private final QuestionRepository questionRepository;

  public DetailQuestionResponse findDetail(Long questionId, MainCategory mainCategory,
      SubCategory subCategory) {
    Question question = questionRepository.findDetail(questionId)
        .orElseThrow(EntityNotFoundException::new);
    List<String> subCategories = subCategory.allOrOneSubCategory(mainCategory);
    Long prevId = questionRepository.findPrevIdById(questionId, mainCategory.name(),
            subCategories).orElse(null);
    Long nextId = questionRepository.findNextIdById(questionId, mainCategory.name()
            ,subCategories).orElse(null);
    question.addViewCount();
    return QuestionConverter.ofDetail(question, question.getTailQuestions(),prevId, nextId);
  }

}
