package com.developer.wiki.question.presentation.comment;

import com.developer.wiki.oauth.User;
import com.developer.wiki.question.command.application.comment.CommentCreateService;
import com.developer.wiki.question.command.application.dto.CreateCommentRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions/{questionId}/comments")
@RequiredArgsConstructor
public class CommentCreateController {

  private final CommentCreateService commentCreateService;

  @PostMapping
  public ResponseEntity<Long> create(@AuthenticationPrincipal User currentUser,
      @PathVariable Long questionId,
      @RequestBody CreateCommentRequest createCommentRequest) {
    Long userId = Objects.isNull(currentUser) ? null : currentUser.getId();
    Long id = commentCreateService.create(questionId, createCommentRequest,userId);
    return ResponseEntity.ok(id);
  }
}
