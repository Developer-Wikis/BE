package com.developer.wiki.question.command.application.comment;

import com.developer.wiki.common.exception.UnAuthorizedException;
import com.developer.wiki.question.command.application.dto.ModifyCommentRequest;
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
public class CommentModifyService {

  private final CommentRepository commentRepository;

  public void modify(Long id, ModifyCommentRequest modifyCommentRequest, Long userId) {
    Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    checkAuthorization(userId, comment);
    comment.matchPassword(modifyCommentRequest.getPassword());
    comment.changePassword(modifyCommentRequest.getPassword());
    comment.changeContent(modifyCommentRequest.getContent());
  }

  private void checkAuthorization(Long userId, Comment comment) {
    if ((!Objects.isNull(comment.getUserId()) && Objects.isNull(userId)) || (
        Objects.isNull(comment.getUserId()) && !Objects.isNull(userId))) {
      throw new UnAuthorizedException("권한이 필요합니다.");
    }
  }
}

