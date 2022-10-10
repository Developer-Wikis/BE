package com.developer.wiki.question.util;

import com.developer.wiki.question.command.application.dto.CreateQuestionRequest;
import com.developer.wiki.question.command.domain.MainCategory;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.SubCategory;
import com.developer.wiki.question.query.application.DetailQuestionResponse;
import com.developer.wiki.question.query.application.SummaryQuestionResponse;

public class QuestionConverter {

  public static Question toQuestion(CreateQuestionRequest createQuestionRequest) {
    return new Question(createQuestionRequest.getTitle(),
        MainCategory.of(createQuestionRequest.getMainCategory()),
        SubCategory.of(createQuestionRequest.getSubCategory()),
        createQuestionRequest.getAdditionQuestions());
  }

  public static SummaryQuestionResponse ofSummary(Question question) {
    return SummaryQuestionResponse.builder().id(question.getId()).title(question.getTitle())
        .mainCategory(question.getMainCategory().name())
        .subCategory(question.getSubCategory().getCategory()).viewCount(question.getViewCount())
        .commentCount(question.getCommentCount()).createdAt(question.getCreatedAt()).build();
  }

  public static DetailQuestionResponse ofDetail(Question question, Long prevId, Long nextId) {
    return DetailQuestionResponse.builder().id(question.getId()).title(question.getTitle())
        .mainCategory(question.getMainCategory().name())
        .subCategory(question.getSubCategory().getCategory())
        .additionQuestions(question.getAdditionQuestions()).viewCount(question.getViewCount())
        .commentCount(question.getCommentCount()).createdAt(question.getCreatedAt()).prevId(prevId)
        .nextId(nextId).build();
  }
}
