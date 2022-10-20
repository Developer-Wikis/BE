package com.developer.wiki.question.command.application.question;

import com.developer.wiki.question.command.application.dto.CreateTailQuestionRequest;
import com.developer.wiki.question.command.domain.EntityNotFoundException;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import com.developer.wiki.question.command.domain.TailQuestion;
import com.developer.wiki.question.command.domain.TailQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionTailService {

  private final QuestionRepository questionRepository;
  private final TailQuestionRepository tailQuestionRepository;

  public Long tailCreate(Long questionId, CreateTailQuestionRequest tailQuestion) {
    Question question = questionRepository.findById(questionId)
        .orElseThrow(EntityNotFoundException::new);
    TailQuestion tail = tailQuestionRepository.save(
        new TailQuestion(tailQuestion.getTailQuestion(), question));
    return tail.getId();
  }
}