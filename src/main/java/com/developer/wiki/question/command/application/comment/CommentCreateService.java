package com.developer.wiki.question.command.application.comment;

import com.developer.wiki.question.command.application.dto.CreateCommentRequest;
import com.developer.wiki.question.command.domain.Comment;
import com.developer.wiki.question.command.domain.CommentRepository;
import com.developer.wiki.question.command.domain.EntityNotFoundException;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import com.developer.wiki.question.util.CommentConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentCreateService {

  private final CommentRepository commentRepository;
  private final QuestionRepository questionRepository;

  public Long create(Long questionId, CreateCommentRequest createCommentRequest) {
    Question question = questionRepository.findById(questionId)
        .orElseThrow(EntityNotFoundException::new);
    Comment comment = CommentConverter.toComment(createCommentRequest, question);
    return commentRepository.save(comment).getId();
  }
}
