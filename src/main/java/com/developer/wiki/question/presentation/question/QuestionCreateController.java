package com.developer.wiki.question.presentation.question;

import com.developer.wiki.question.command.application.CreateQuestionRequest;
import com.developer.wiki.question.command.application.question.QuestionCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionCreateController {

  private final QuestionCreateService questionCreateService;

  @PostMapping
  public ResponseEntity<Long> create(@RequestBody CreateQuestionRequest createQuestionRequest) {
    Long id = questionCreateService.questionCreate(createQuestionRequest);
    return ResponseEntity.ok(id);
  }
}
