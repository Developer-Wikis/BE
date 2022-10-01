package com.developer.wiki.question.presentation.comment;

import com.developer.wiki.question.command.application.comment.CommentDeleteService;
import com.developer.wiki.question.command.application.dto.PasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions/{questionId}/comments")
@RequiredArgsConstructor
public class CommentDeleteController {

  private final CommentDeleteService commentDeleteService;

  @DeleteMapping("/{commentId}")
  public ResponseEntity<Void> delete(@PathVariable Long commentId,
     @RequestBody PasswordRequest passwordRequest) {
    commentDeleteService.delete(commentId, passwordRequest);
    return ResponseEntity.ok(null);
  }
}
