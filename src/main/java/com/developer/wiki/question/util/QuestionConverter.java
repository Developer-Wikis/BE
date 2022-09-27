package com.developer.wiki.question.util;

import com.developer.wiki.question.command.application.dto.CreateQuestionRequest;
import com.developer.wiki.question.command.domain.Question;

public class QuestionConverter {

  public static Question toQuestion(CreateQuestionRequest createQuestionRequest) {
    return new Question(createQuestionRequest.getTitle(), createQuestionRequest.getNickname(),
        PasswordEncrypter.encrypt(createQuestionRequest.getPassword()),
        createQuestionRequest.getCategory(), createQuestionRequest.getAdditionQuestions());
  }
}
