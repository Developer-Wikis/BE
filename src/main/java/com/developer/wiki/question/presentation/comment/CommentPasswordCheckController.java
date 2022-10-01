package com.developer.wiki.question.presentation.comment;

import com.developer.wiki.question.command.application.comment.CommentPasswordCheckService;
import com.developer.wiki.question.command.application.dto.PasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions/{questionId}/comments")
@RequiredArgsConstructor
public class CommentPasswordCheckController {

  private final CommentPasswordCheckService commentPasswordCheckService;

  @PostMapping("/{commentId}/check")
  public ResponseEntity<Boolean> checkPassword(@PathVariable Long commentId,
      @RequestBody PasswordRequest passwordRequest) {
    boolean isMatch = commentPasswordCheckService.checkPassword(commentId, passwordRequest);
    return ResponseEntity.ok(isMatch);
  }
}
