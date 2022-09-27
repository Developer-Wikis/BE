package com.developer.wiki.question.command.application.comment;

import com.developer.wiki.question.command.application.dto.ModifyCommentRequest;
import com.developer.wiki.question.command.domain.Comment;
import com.developer.wiki.question.command.domain.CommentRepository;
import com.developer.wiki.question.command.domain.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentModifyService {

  private final CommentRepository commentRepository;

  public void modify(Long id, ModifyCommentRequest modifyCommentRequest) {
    Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    comment.matchPassword(modifyCommentRequest.getPassword());
    comment.changePassword(modifyCommentRequest.getPassword());
    comment.changeContent(modifyCommentRequest.getContent());
  }
}
