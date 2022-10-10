package com.developer.wiki.question.presentation.question;

import com.developer.wiki.question.command.domain.MainCategory;
import com.developer.wiki.question.command.domain.SubCategory;
import com.developer.wiki.question.query.application.DetailQuestionResponse;
import com.developer.wiki.question.query.application.QuestionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionDetailController {

  private final QuestionDetailService questionDetailService;

  @GetMapping("/{questionId}")
  public ResponseEntity<DetailQuestionResponse> getDetail(@PathVariable Long questionId,
      @RequestParam(required = false) String mainCategory,@RequestParam(required = false) String subCategory) {
    DetailQuestionResponse detail = questionDetailService.findDetail(questionId,
        MainCategory.of(mainCategory), SubCategory.of(subCategory));
    return ResponseEntity.ok(detail);
  }
}
