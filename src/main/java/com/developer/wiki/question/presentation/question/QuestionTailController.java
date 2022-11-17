package com.developer.wiki.question.presentation.question;

import com.developer.wiki.common.exception.UnAuthorizedException;
import com.developer.wiki.oauth.User;
import com.developer.wiki.question.command.application.dto.CreateTailQuestionRequest;
import com.developer.wiki.question.command.application.question.QuestionTailService;
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
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionTailController {

  private final QuestionTailService questionTailService;

  @PostMapping("/{questionId}/tail")
  public ResponseEntity<Long> tailCreate(@AuthenticationPrincipal User currentUser,
      @PathVariable Long questionId, @RequestBody CreateTailQuestionRequest tailQuestion) {
    if (Objects.isNull(currentUser)) {
      throw new UnAuthorizedException("토큰이 필요합니다.");
    }
    Long id = questionTailService.tailCreate(questionId, tailQuestion);
    return ResponseEntity.ok(id);
  }
}
