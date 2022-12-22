package com.developer.wiki.question.presentation.question;

import com.developer.wiki.oauth.User;
import com.developer.wiki.question.query.application.QuestionSearchService;
import com.developer.wiki.question.query.application.SummaryQuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionSearchController {

  private final QuestionSearchService questionSearchService;

  @GetMapping("/search")
  public ResponseEntity<Slice<SummaryQuestionResponse>> getSlice(
      @AuthenticationPrincipal User currentUser, @RequestParam String keyword, Pageable pageable) {
    Slice<SummaryQuestionResponse> summarySlice = questionSearchService.findPage(pageable,
        currentUser, keyword);
    return ResponseEntity.ok(summarySlice);
  }
}
