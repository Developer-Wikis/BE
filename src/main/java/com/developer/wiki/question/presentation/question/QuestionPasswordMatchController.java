package com.developer.wiki.question.presentation.question;

import com.developer.wiki.question.command.application.dto.PasswordRequest;
import com.developer.wiki.question.command.application.question.QuestionPasswordMatchService;
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
public class QuestionPasswordMatchController {

  private final QuestionPasswordMatchService questionPasswordMatchService;

  @PostMapping("{questionId}/match")
  public ResponseEntity<Void> matchPassword(@PathVariable Long questionId,
      @RequestBody PasswordRequest passwordRequest) {
    questionPasswordMatchService.matchPassword(questionId, passwordRequest);
    return ResponseEntity.ok(null);
  }
}
