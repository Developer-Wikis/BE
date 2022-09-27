package com.developer.wiki.question.command;

import com.developer.wiki.question.command.application.CreateCommentRequest;
import com.developer.wiki.question.command.domain.Comment;
import com.developer.wiki.question.command.domain.Question;

public class CommentConverter {

  public static Comment toComment(CreateCommentRequest createCommentRequest, Question question) {
    return new Comment(createCommentRequest.getNickname(),
        PasswordEncrypter.encrypt(createCommentRequest.getPassword()),
        createCommentRequest.getContent(),question);
  }
}
