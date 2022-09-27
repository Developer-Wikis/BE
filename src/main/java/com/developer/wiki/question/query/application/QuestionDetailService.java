package com.developer.wiki.question.query.application;

import com.developer.wiki.question.command.domain.EntityNotFoundException;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import com.developer.wiki.question.util.QuestionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionDetailService {

  private final QuestionRepository questionRepository;

  public DetailQuestionResponse findDetail(Long questionId) {
    Question question = questionRepository.findDetail(questionId)
        .orElseThrow(EntityNotFoundException::new);
    return QuestionConverter.ofDetail(question);
  }
}
