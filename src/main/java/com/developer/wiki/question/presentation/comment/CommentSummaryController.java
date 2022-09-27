package com.developer.wiki.question.presentation.comment;

import com.developer.wiki.question.query.application.CommentSummaryService;
import com.developer.wiki.question.query.application.SummaryCommentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions/{questionId}/comments")
@RequiredArgsConstructor
public class CommentSummaryController {

  private final CommentSummaryService commentSummaryService;

  @GetMapping
  public ResponseEntity<List<SummaryCommentResponse>> getList(@PathVariable Long questionId) {
    List<SummaryCommentResponse> list = commentSummaryService.findList(questionId);
    return ResponseEntity.ok(list);
  }
}
