package com.developer.wiki.question.command.application.question;

import com.developer.wiki.question.command.application.dto.CreateQuestionRequest;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import com.developer.wiki.question.util.QuestionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionCreateService {

  private final QuestionRepository questionRepository;

  public Long create(CreateQuestionRequest createQuestionRequest) {
    Question question = QuestionConverter.toQuestion(createQuestionRequest);
    return questionRepository.save(question).getId();
  }
}
