package com.developer.wiki.question.command.application.question;

import com.developer.wiki.question.command.application.PasswordRequest;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionPasswordMatchService {

  private final QuestionRepository questionRepository;

  public void matchPassword(Long id, PasswordRequest passwordRequest) {
    Question question = questionRepository.findById(id).orElseThrow(EntityExistsException::new);
    question.matchPassword(passwordRequest.getPassword());
  }
}
