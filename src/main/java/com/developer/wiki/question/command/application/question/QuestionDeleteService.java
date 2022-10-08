package com.developer.wiki.question.command.application.question;

import com.developer.wiki.question.command.application.dto.PasswordRequest;
import com.developer.wiki.question.command.domain.EntityNotFoundException;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionDeleteService {

  private final QuestionRepository questionRepository;

  public void delete(Long id, PasswordRequest passwordRequest) {
    throw new UnsupportedOperationException();
//    Question question = questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//    questionRepository.delete(question);
  }
}
