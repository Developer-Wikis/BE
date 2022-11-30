package com.developer.wiki.question.presentation.comment;

import com.developer.wiki.oauth.User;
import com.developer.wiki.question.command.application.comment.CommentModifyService;
import com.developer.wiki.question.command.application.dto.ModifyCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/questions/{questionId}/comments")
@RequiredArgsConstructor
public class CommentModifyController {

  private final CommentModifyService commentModifyService;

  @PutMapping("/{commentId}")
  public ResponseEntity<Void> modify(@AuthenticationPrincipal User currentUser,
      @PathVariable Long commentId, @RequestBody ModifyCommentRequest modifyCommentRequest) {
    Long userId = Objects.isNull(currentUser) ? null : currentUser.getId();
    commentModifyService.modify(commentId, modifyCommentRequest, userId);
    return ResponseEntity.ok(null);
  }
}
