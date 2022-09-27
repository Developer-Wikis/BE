package com.developer.wiki.question.command.application;

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

  public void matchPassword(Long id, MatchPasswordRequest passwordRequest) {
    Question question = questionRepository.findById(id).orElseThrow(EntityExistsException::new);
    question.matchPassword(passwordRequest.getPassword());
  }
}
