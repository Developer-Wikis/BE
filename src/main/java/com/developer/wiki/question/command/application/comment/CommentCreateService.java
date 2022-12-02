package com.developer.wiki.question.command.application.comment;

import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.oauth.User;
import com.developer.wiki.oauth.UserRepository;
import com.developer.wiki.question.command.application.dto.CreateCommentRequest;
import com.developer.wiki.question.command.domain.Comment;
import com.developer.wiki.question.command.domain.CommentRepository;
import com.developer.wiki.question.command.domain.EntityNotFoundException;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import com.developer.wiki.question.util.CommentConverter;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentCreateService {

  private final CommentRepository commentRepository;
  private final QuestionRepository questionRepository;
  private final UserRepository userRepository;

  public Long create(Long questionId, CreateCommentRequest createCommentRequest, Long userId) {
    Question question = questionRepository.findById(questionId)
        .orElseThrow(EntityNotFoundException::new);
    return Objects.isNull(userId) ? createdByAnonymous(createCommentRequest, question)
        : createdByUser(createCommentRequest, userRepository.findById(userId)
            .orElseThrow(() -> new BadRequestException("유저가 존재하지 않습니다.")), question);
  }

  private Long createdByAnonymous(CreateCommentRequest createCommentRequest, Question question) {
    Comment comment = CommentConverter.toComment(createCommentRequest, question);
    return commentRepository.save(comment).getId();
  }

  private Long createdByUser(CreateCommentRequest createCommentRequest, User user,
      Question question) {
    Comment comment = CommentConverter.toCommentByUser(createCommentRequest, question, user);
    return commentRepository.save(comment).getId();
  }

}
