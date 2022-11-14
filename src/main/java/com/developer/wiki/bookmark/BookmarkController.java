package com.developer.wiki.oauth.controller;

import com.developer.wiki.oauth.User;
import com.developer.wiki.oauth.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookmarka")
public class BookmarkController {
  private final BookmarkService bookmarkService;

  @PostMapping("/{questionId}")
  public ResponseEntity getUserInfo(@AuthenticationPrincipal User currentUser,
      @PathVariable Long questionId) {
    bookmarkService.bookmark
    return ResponseEntity.ok().build();
  }
}
