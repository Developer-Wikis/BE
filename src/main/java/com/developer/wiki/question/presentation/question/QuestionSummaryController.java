package com.developer.wiki.question.presentation.question;

import com.developer.wiki.oauth.User;
import com.developer.wiki.question.query.application.QuestionSummaryService;
import com.developer.wiki.question.query.application.SummaryQuestionResponse;
import java.util.List;
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
public class QuestionSummaryController {

  private final QuestionSummaryService questionSummaryService;

  @GetMapping
  public ResponseEntity<Slice<SummaryQuestionResponse>> getSlice(
      @AuthenticationPrincipal User currentUser,
      @RequestParam(required = false) String mainCategory,
      @RequestParam(required = false) List<String> subCategory, Pageable pageable) {
    Slice<SummaryQuestionResponse> summarySlice = questionSummaryService.findSlice(pageable,
        mainCategory, subCategory,currentUser);
    return ResponseEntity.ok(summarySlice);
  }
}
