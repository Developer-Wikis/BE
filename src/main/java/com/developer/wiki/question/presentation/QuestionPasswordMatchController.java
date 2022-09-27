package com.developer.wiki.question.presentation;

import com.developer.wiki.question.command.application.MatchPasswordRequest;
import com.developer.wiki.question.command.application.QuestionPasswordMatchService;
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
      @RequestBody MatchPasswordRequest matchPasswordRequest) {
    questionPasswordMatchService.matchPassword(questionId, matchPasswordRequest);
    return ResponseEntity.ok(null);
  }
}
