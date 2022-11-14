package com.developer.wiki.oauth.controller;

import com.developer.wiki.oauth.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookmark")
public class BookmarkController {

  @PostMapping("/{questionId}")
  public ResponseEntity getUserInfo(@AuthenticationPrincipal User currentUser,
      @PathVariable Long questionId) {
    return ResponseEntity.ok().build();
  }
}
