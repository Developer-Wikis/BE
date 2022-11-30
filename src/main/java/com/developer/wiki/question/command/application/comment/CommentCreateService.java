package com.developer.wiki.question.command.application.comment;

import com.developer.wiki.question.command.application.dto.CreateCommentRequest;
import com.developer.wiki.question.command.domain.*;
import com.developer.wiki.question.util.CommentConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentCreateService {

  private final CommentRepository commentRepository;
  private final QuestionRepository questionRepository;

  public Long create(Long questionId, CreateCommentRequest createCommentRequest, Long userId) {
    Question question = questionRepository.findById(questionId)
        .orElseThrow(EntityNotFoundException::new);
    return Objects.isNull(userId) ? createdByAnonymous(createCommentRequest, question)
        : createdByUser(createCommentRequest, userId, question);
  }

  private Long createdByAnonymous(CreateCommentRequest createCommentRequest, Question question) {
    Comment comment = CommentConverter.toComment(createCommentRequest, question);
    return commentRepository.save(comment).getId();
  }

  private Long createdByUser(CreateCommentRequest createCommentRequest, Long userId,
      Question question) {
    Comment comment = CommentConverter.toCommentByUser(createCommentRequest, question, userId);
    return commentRepository.save(comment).getId();
  }

}
