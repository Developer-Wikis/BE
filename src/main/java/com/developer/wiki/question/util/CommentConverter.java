package com.developer.wiki.question.util;

import com.developer.wiki.question.command.application.dto.CreateCommentRequest;
import com.developer.wiki.question.command.domain.Comment;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.query.application.SummaryCommentResponse;

import java.util.Objects;

public class CommentConverter {

  public static Comment toComment(CreateCommentRequest createCommentRequest, Question question) {
    return new Comment(createCommentRequest.getNickname(),
        PasswordEncrypter.encrypt(createCommentRequest.getPassword()),
        createCommentRequest.getContent(), question);
  }

  public static Comment toCommentByUser(CreateCommentRequest createCommentRequest,
      Question question, Long userId) {
    return new Comment(createCommentRequest.getNickname(),
        PasswordEncrypter.encrypt(createCommentRequest.getPassword()),
        createCommentRequest.getContent(), question, userId);
  }

  public static SummaryCommentResponse ofSummary(Comment comment) {
    return SummaryCommentResponse.builder().id(comment.getId()).username(comment.getNickname())
        .role(comment.getCommentRole().name())
        .userId(Objects.isNull(comment.getId()) ? null : comment.getUserId())
        .content(comment.getContent()).createdAt(comment.getCreatedAt()).build();
  }
}
