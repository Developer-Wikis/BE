package com.developer.wiki.question.command.application.comment;

import com.developer.wiki.question.command.application.dto.PasswordRequest;
import com.developer.wiki.question.command.domain.Comment;
import com.developer.wiki.question.command.domain.CommentRepository;
import com.developer.wiki.question.command.domain.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentPasswordCheckService {

  private final CommentRepository commentRepository;

  public boolean checkPassword(Long id, PasswordRequest passwordRequest) {
    Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    return comment.checkPassword(passwordRequest.getPassword());
  }
}
