package com.developer.wiki.question.util;

import com.developer.wiki.question.command.application.dto.CreateQuestionRequest;
import com.developer.wiki.question.command.domain.Category;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.query.application.DetailQuestionResponse;
import com.developer.wiki.question.query.application.SummaryQuestionResponse;
import java.util.List;

public class QuestionConverter {

  public static Question toQuestion(CreateQuestionRequest createQuestionRequest) {
    return new Question(createQuestionRequest.getTitle(), createQuestionRequest.getNickname(),
        PasswordEncrypter.encrypt(createQuestionRequest.getPassword()),
        Category.of(createQuestionRequest.getCategory()),
        createQuestionRequest.getAdditionQuestions());
  }

  public static SummaryQuestionResponse ofSummary(Question question) {
    return SummaryQuestionResponse.builder().id(question.getId()).title(question.getTitle())
        .nickname(question.getNickname()).category(question.getCategory())
        .viewCount(question.getViewCount()).commentCount(question.getCommentCount())
        .createdAt(question.getCreatedAt()).build();
  }

  public static DetailQuestionResponse ofDetail(Question question,Long prevId,Long nextId) {
    return DetailQuestionResponse.builder().id(question.getId()).title(question.getTitle())
        .nickname(question.getNickname()).category(question.getCategory())
        .additionQuestions(question.getAdditionQuestions()).viewCount(question.getViewCount())
        .commentCount(question.getCommentCount()).createdAt(question.getCreatedAt()).prevId(prevId).nextId(nextId).build();
  }
}
