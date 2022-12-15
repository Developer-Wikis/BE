package com.developer.wiki.question.util;

import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.oauth.User;
import com.developer.wiki.question.command.application.dto.CreateCommentRequest;
import com.developer.wiki.question.command.domain.Comment;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.query.application.SummaryCommentResponse;
import java.util.Objects;

public class CommentConverter {

  public static Comment toComment(CreateCommentRequest createCommentRequest, Question question) {
    if (Objects.isNull(createCommentRequest.getNickname()) || Objects.isNull(
        createCommentRequest.getPassword())) {
      throw new BadRequestException("익명으로 등록시엔 nickname, password를 입력해야 합니다.");
    }
    return new Comment(createCommentRequest.getNickname(),
        PasswordEncrypter.encrypt(createCommentRequest.getPassword()),
        createCommentRequest.getContent(), question);
  }

  public static Comment toCommentByUser(CreateCommentRequest createCommentRequest,
      Question question, User user) {
    return new Comment(createCommentRequest.getContent(), question, user);
  }

  public static SummaryCommentResponse ofSummary(Comment comment) {
    return SummaryCommentResponse.builder().id(comment.getId()).username(Objects.isNull(comment.getUser()) ? comment.getNickname() : comment.getUser().getName())
        .role(comment.getCommentRole().name())
        .userId(Objects.isNull(comment.getUser()) ? null : comment.getUser().getId())
        .profileUrl(Objects.isNull(comment.getUser()) ? null : comment.getUser().getProfileUrl())
        .content(comment.getContent()).createdAt(comment.getCreatedAt()).build();
  }
}
