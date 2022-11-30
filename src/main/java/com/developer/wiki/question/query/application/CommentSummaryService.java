package com.developer.wiki.question.query.application;

import com.developer.wiki.question.command.domain.Comment;
import com.developer.wiki.question.command.domain.CommentRepository;
import com.developer.wiki.question.util.CommentConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentSummaryService {

  private final CommentRepository commentRepository;

  public List<SummaryCommentResponse> findList(Long questionId) {
    List<Comment> comments = commentRepository.findByQuestion_Id(questionId);
    return comments.stream().map(CommentConverter::ofSummary).collect(Collectors.toList());
  }

  public Long getMyCommentSize(Long userId){
    return commentRepository.countByUserId(userId);
  }
}
