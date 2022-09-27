package com.developer.wiki.question.command.application;

import com.developer.wiki.question.command.domain.Comment;
import com.developer.wiki.question.command.domain.CommentRepository;
import com.developer.wiki.question.command.domain.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentDeleteService {

  private final CommentRepository commentRepository;

  public void commentDelete(Long id, PasswordRequest passwordRequest) {
    Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    comment.matchPassword(passwordRequest.getPassword());
    commentRepository.delete(comment);
  }
}
