package com.developer.wiki.question.command.application.comment;

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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentPasswordCheckService {

  private final CommentRepository commentRepository;

  public boolean checkPassword(Long id, PasswordRequest passwordRequest, Long userId) {
    Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    //Null이 아니면서, id도 맞지 않을때
    if (!Objects.isNull(userId) && !userId.equals(comment.getUser().getId())) {
      throw new UnAuthorizedException("수정 권한이 없습니다.");
    }
    return comment.checkPassword(passwordRequest.getPassword());
  }
}
