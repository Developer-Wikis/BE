package com.developer.wiki.question.command.application.question;

import com.developer.wiki.question.command.application.ModifyQuestionRequest;
import com.developer.wiki.question.command.domain.EntityNotFoundException;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionModifyService {

  private final QuestionRepository questionRepository;

  public void modify(Long id, ModifyQuestionRequest modifyQuestionRequest) {
    Question question = questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    question.changePassword(modifyQuestionRequest.getPassword());
    question.changeTitle(modifyQuestionRequest.getTitle());
    question.changeCategory(modifyQuestionRequest.getCategory());
    question.changeAdditionQuestions(modifyQuestionRequest.getAdditionQuestions());
  }
}
