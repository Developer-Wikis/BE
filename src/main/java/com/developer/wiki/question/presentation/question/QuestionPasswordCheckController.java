package com.developer.wiki.question.presentation.question;

import com.developer.wiki.question.command.application.dto.PasswordRequest;
import com.developer.wiki.question.command.application.question.QuestionPasswordCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionPasswordCheckController {

  private final QuestionPasswordCheckService questionPasswordCheckService;

  @PostMapping("/{questionId}/check")
  public ResponseEntity<Boolean> checkPassword(@PathVariable Long questionId,
      @RequestBody PasswordRequest passwordRequest) {
    boolean isMatch = questionPasswordCheckService.checkPassword(questionId, passwordRequest);
    return ResponseEntity.ok(isMatch);
  }
}
