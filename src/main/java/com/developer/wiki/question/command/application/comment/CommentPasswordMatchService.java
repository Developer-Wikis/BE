package com.developer.wiki.question.command.application.comment;

import com.developer.wiki.question.command.application.dto.PasswordRequest;
import com.developer.wiki.question.command.domain.Comment;
import com.developer.wiki.question.command.domain.CommentRepository;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentPasswordMatchService {

  private final CommentRepository commentRepository;

  public void matchPassword(Long id, PasswordRequest passwordRequest) {
    Comment comment = commentRepository.findById(id).orElseThrow(EntityExistsException::new);
    comment.matchPassword(passwordRequest.getPassword());
  }
}
