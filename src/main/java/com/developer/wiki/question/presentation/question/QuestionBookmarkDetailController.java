package com.developer.wiki.question.presentation.question;

import com.developer.wiki.common.exception.UnAuthorizedException;
import com.developer.wiki.oauth.User;
import com.developer.wiki.question.command.domain.MainCategory;
import com.developer.wiki.question.command.domain.SubCategory;
import com.developer.wiki.question.query.application.DetailQuestionResponse;
import com.developer.wiki.question.query.application.QuestionBookmarkedDetailService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionBookmarkDetailController {

  private final QuestionBookmarkedDetailService questionBookmarkedDetailService;

  @GetMapping("/bookmark/{questionId}")
  public ResponseEntity<DetailQuestionResponse> getDetail(@AuthenticationPrincipal User currentUser,
      @PathVariable Long questionId, @RequestParam(required = false) String mainCategory,
      @RequestParam(required = false) String subCategory) {
    if (Objects.isNull(currentUser)) {
      throw new UnAuthorizedException("토큰이 필요합니다.");
    }
    DetailQuestionResponse detail = questionBookmarkedDetailService.findDetail(questionId,
        MainCategory.of(mainCategory), SubCategory.of(subCategory), currentUser);
    return ResponseEntity.ok(detail);
  }
}
