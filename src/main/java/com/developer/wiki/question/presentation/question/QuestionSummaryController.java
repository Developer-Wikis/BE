package com.developer.wiki.question.presentation.question;

import com.developer.wiki.question.query.application.QuestionSummaryService;
import com.developer.wiki.question.query.application.SummaryQuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionSummaryController {

  private final QuestionSummaryService questionSummaryService;

  @GetMapping
  public ResponseEntity<Slice<SummaryQuestionResponse>> getSlice(Pageable pageable) {
    Slice<SummaryQuestionResponse> summarySlice = questionSummaryService.findSlice(pageable);
    return ResponseEntity.ok(summarySlice);
  }
}
