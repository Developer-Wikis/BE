package com.developer.wiki.question.command.application.question;

import com.developer.wiki.question.command.application.dto.PasswordRequest;
import com.developer.wiki.question.command.domain.EntityNotFoundException;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionPasswordCheckService {

  private final QuestionRepository questionRepository;

  public boolean checkPassword(Long id, PasswordRequest passwordRequest) {
    Question question = questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    //return question.checkPassword(passwordRequest.getPassword());
    throw new UnsupportedOperationException();
  }
}
