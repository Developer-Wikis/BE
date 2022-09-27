package com.developer.wiki.question.util;

import com.developer.wiki.question.command.application.dto.CreateQuestionRequest;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.query.application.SummaryQuestionResponse;

public class QuestionConverter {

  public static Question toQuestion(CreateQuestionRequest createQuestionRequest) {
    return new Question(createQuestionRequest.getTitle(), createQuestionRequest.getNickname(),
        PasswordEncrypter.encrypt(createQuestionRequest.getPassword()),
        createQuestionRequest.getCategory(), createQuestionRequest.getAdditionQuestions());
  }

  public static SummaryQuestionResponse ofSummary(Question question) {
    return SummaryQuestionResponse.builder().id(question.getId()).title(question.getTitle())
        .nickname(question.getNickname()).category(question.getCategory())
        .viewCount(question.getViewCount()).commentCount(question.getCommentCount())
        .createdAt(question.getCreatedAt()).build();
  }
}
