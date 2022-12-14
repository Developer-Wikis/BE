package com.developer.wiki.question.presentation.question;

import com.developer.wiki.question.command.application.dto.ModifyQuestionRequest;
import com.developer.wiki.question.command.application.question.QuestionModifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionModifyController {

  private final QuestionModifyService questionModifyService;

  @PutMapping("/{questionId}")
  public ResponseEntity<Void> modify(@PathVariable Long questionId,
      @RequestBody ModifyQuestionRequest modifyQuestionRequest) {
    questionModifyService.modify(questionId, modifyQuestionRequest);
    return ResponseEntity.ok(null);
  }
}
