package com.developer.wiki.question.presentation.question;

import com.developer.wiki.question.command.application.dto.PasswordRequest;
import com.developer.wiki.question.command.application.question.QuestionDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionDeleteController {

  private final QuestionDeleteService questionDeleteService;

  @DeleteMapping("/{questionId}")
  public ResponseEntity<Void> delete(@PathVariable Long questionId, @RequestBody PasswordRequest passwordRequest) {
    questionDeleteService.delete(questionId,passwordRequest);
    return ResponseEntity.ok(null);
  }
}
