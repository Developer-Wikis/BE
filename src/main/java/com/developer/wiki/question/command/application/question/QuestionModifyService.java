package com.developer.wiki.question.command.application.question;

import com.developer.wiki.question.command.application.dto.ModifyQuestionRequest;
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
    throw new UnsupportedOperationException();
//    Question question = questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//    question.changeTitle(modifyQuestionRequest.getTitle());
//    question.changeTailQuestions(modifyQuestionRequest.getAdditionQuestions().stream()
//        .map(q -> new TailQuestion(q, question)).collect(Collectors.toList()));
  }
}
