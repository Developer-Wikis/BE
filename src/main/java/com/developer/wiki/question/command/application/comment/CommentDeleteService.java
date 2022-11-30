package com.developer.wiki.question.command.application.comment;

import com.developer.wiki.common.exception.UnAuthorizedException;
import com.developer.wiki.question.command.application.dto.PasswordRequest;
import com.developer.wiki.question.command.domain.Comment;
import com.developer.wiki.question.command.domain.CommentRepository;
import com.developer.wiki.question.command.domain.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentDeleteService {

  private final CommentRepository commentRepository;

  public void delete(Long id, PasswordRequest passwordRequest, Long userId) {
    Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    checkAuthorization(userId, comment);
    comment.matchPassword(passwordRequest.getPassword());
    commentRepository.delete(comment);
  }

  private void checkAuthorization(Long userId, Comment comment) {
    if ((!Objects.isNull(comment.getUserId()) && Objects.isNull(userId)) || (
        Objects.isNull(comment.getUserId()) && !Objects.isNull(userId))) {
      throw new UnAuthorizedException("권한이 필요합니다.");
    }
  }
}
