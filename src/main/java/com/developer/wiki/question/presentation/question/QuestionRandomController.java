package com.developer.wiki.question.presentation.question;

import com.developer.wiki.oauth.User;
import com.developer.wiki.question.query.application.QuestionRandomService;
import com.developer.wiki.question.query.application.RandomQuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionRandomController {

  private final QuestionRandomService questionRandomService;

  @GetMapping("/random")
  public ResponseEntity<Slice<RandomQuestionResponse>> random(
      @AuthenticationPrincipal User currentUser, @RequestParam String mainCategory,
      @RequestParam List<String> subCategory) {
    Slice<RandomQuestionResponse> randomSlice = questionRandomService.findRandomPage(
        PageRequest.of(0, 300), mainCategory, subCategory, currentUser);
    return ResponseEntity.ok(randomSlice);
  }
}