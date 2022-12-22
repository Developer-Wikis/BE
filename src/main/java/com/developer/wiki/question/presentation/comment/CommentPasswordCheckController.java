package com.developer.wiki.question.presentation.comment;

import com.developer.wiki.oauth.User;
import com.developer.wiki.question.command.application.comment.CommentPasswordCheckService;
import com.developer.wiki.question.command.application.dto.PasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/questions/{questionId}/comments")
@RequiredArgsConstructor
public class CommentPasswordCheckController {

  private final CommentPasswordCheckService commentPasswordCheckService;

  @PostMapping("/{commentId}/check")
  public ResponseEntity<Boolean> checkPassword(@AuthenticationPrincipal User currentUser,
      @PathVariable Long commentId, @RequestBody PasswordRequest passwordRequest) {
    Long userId = Objects.isNull(currentUser) ? null : currentUser.getId();
    boolean isMatch = commentPasswordCheckService.checkPassword(commentId, passwordRequest,userId);
    return ResponseEntity.ok(isMatch);
  }
}
