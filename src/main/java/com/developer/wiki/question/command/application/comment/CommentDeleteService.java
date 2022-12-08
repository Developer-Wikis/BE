package com.developer.wiki.question.command.application.comment;

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
    if (!Objects.isNull(userId)) {
      commentRepository.delete(comment);
    }
    else if(Objects.isNull(userId) && Objects.nonNull(passwordRequest)){
      comment.matchPassword(passwordRequest.getPassword());
      commentRepository.delete(comment);
    }
  }
}
