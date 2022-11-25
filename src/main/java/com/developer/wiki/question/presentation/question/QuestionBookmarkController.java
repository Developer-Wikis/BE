package com.developer.wiki.question.presentation.question;

import com.developer.wiki.common.exception.UnAuthorizedException;
import com.developer.wiki.oauth.User;
import com.developer.wiki.question.query.application.QuestionBookmarkService;
import com.developer.wiki.question.query.application.SummaryQuestionResponse;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionBookmarkController {

  private final QuestionBookmarkService questionBookmarkService;

  @GetMapping("/bookmark")
  public ResponseEntity<Page<SummaryQuestionResponse>> getSlice(
      @AuthenticationPrincipal User currentUser,
      @RequestParam(required = false) String mainCategory,
      @RequestParam(required = false) List<String> subCategory, Pageable pageable) {
    if (Objects.isNull(currentUser)) {
      throw new UnAuthorizedException("토큰이 필요합니다.");
    }
    Page<SummaryQuestionResponse> summarySlice = questionBookmarkService.findBookmarkedList(
        currentUser.getId(), pageable, mainCategory, subCategory);
    return ResponseEntity.ok(summarySlice);
  }
}
