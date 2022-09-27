package com.developer.wiki.question.command.application;

import com.developer.wiki.question.command.QuestionConverter;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionCreateService {

  private final QuestionRepository questionRepository;

  public Long questionCreate(CreateQuestionRequest createQuestionRequest) {
    Question question = QuestionConverter.toQuestion(createQuestionRequest);
    return questionRepository.save(question).getId();
  }
}
