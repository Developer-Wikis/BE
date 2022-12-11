package com.developer.wiki.question.command.application.comment;

import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.common.exception.UnAuthorizedException;
import com.developer.wiki.question.command.application.dto.PasswordRequest;
import com.developer.wiki.question.command.domain.Comment;
import com.developer.wiki.question.command.domain.CommentRepository;
import com.developer.wiki.question.command.domain.EntityNotFoundException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentDeleteService {

  private final CommentRepository commentRepository;

  public void delete(Long id, PasswordRequest passwordRequest, Long userId) {
    Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    checkInvalidAuthorization(passwordRequest, userId, comment);
    commentRepository.delete(comment);
  }

  private void checkInvalidAuthorization(PasswordRequest passwordRequest, Long userId,
      Comment comment) {
    if (checkInvalidUser(userId, comment)) {
      throw new UnAuthorizedException("댓글 삭제 권한이 없습니다.");
    } else if (checkInvalidAnonymous(passwordRequest, userId, comment)) {
      throw new UnAuthorizedException("댓글 삭제를 할 수 없습니다.");
    }
  }

  private boolean checkInvalidUser(Long userId, Comment comment) {
    return Objects.nonNull(userId) && (Objects.isNull(comment.getUser()) || !comment.getUser()
        .getId().equals(userId));
  }

  private boolean checkInvalidAnonymous(PasswordRequest passwordRequest, Long userId,
      Comment comment) {
    if (Objects.isNull(userId) && !comment.checkPassword(passwordRequest.getPassword())) {
      throw new BadRequestException("비밀번호가 틀렸습니다.");
    }
    return Objects.isNull(userId) && Objects.isNull(passwordRequest);
  }
}
