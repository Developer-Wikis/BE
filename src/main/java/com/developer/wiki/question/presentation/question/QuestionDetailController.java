package com.developer.wiki.question.presentation.question;

import com.developer.wiki.oauth.User;
import com.developer.wiki.question.command.domain.MainCategory;
import com.developer.wiki.question.command.domain.SubCategory;
import com.developer.wiki.question.query.application.DetailQuestionResponse;
import com.developer.wiki.question.query.application.QuestionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionDetailController {

  private final QuestionDetailService questionDetailService;

  @GetMapping("/{questionId}")
  public ResponseEntity<DetailQuestionResponse> getDetail(@AuthenticationPrincipal User currentUser,
      @PathVariable Long questionId, @RequestParam(required = false) String mainCategory,
      @RequestParam(required = false) String subCategory) {
    DetailQuestionResponse detail = questionDetailService.findDetail(questionId,
        MainCategory.of(mainCategory), SubCategory.of(subCategory), currentUser);
    return ResponseEntity.ok(detail);
  }
}
