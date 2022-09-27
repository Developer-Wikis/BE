package com.developer.wiki.question.presentation.comment;

import com.developer.wiki.question.command.application.comment.CommentModifyService;
import com.developer.wiki.question.command.application.dto.ModifyCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions/{questionId}/comments")
@RequiredArgsConstructor
public class CommentModifyController {

  private final CommentModifyService commentModifyService;

  @PutMapping("/{commentId}")
  public ResponseEntity<Void> modify(@PathVariable Long commentId,
      @RequestBody ModifyCommentRequest modifyCommentRequest) {
    commentModifyService.modify(commentId, modifyCommentRequest);
    return ResponseEntity.ok(null);
  }
}