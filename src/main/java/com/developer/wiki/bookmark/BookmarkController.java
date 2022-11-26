package com.developer.wiki.bookmark;

import com.developer.wiki.common.exception.UnAuthorizedException;
import com.developer.wiki.oauth.User;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookmarks")
public class BookmarkController {

  private final BookmarkService bookmarkService;

  @PostMapping("/{questionId}")
  public ResponseEntity<Boolean> toggle(@AuthenticationPrincipal User currentUser,
      @PathVariable Long questionId) {
    if (Objects.isNull(currentUser)) {
      throw new UnAuthorizedException("토큰이 필요합니다.");
    }
    Boolean isBookmarked = bookmarkService.toggle(questionId, currentUser.getId());
    return ResponseEntity.ok(isBookmarked);
  }

  @GetMapping("/{questionId}")
  public ResponseEntity<Boolean> getBookmarked(@AuthenticationPrincipal User currentUser,
      @PathVariable Long questionId) {
    if (Objects.isNull(currentUser)) {
      throw new UnAuthorizedException("토큰이 필요합니다.");
    }
    Boolean isBookmarked = bookmarkService.getBookmarked(questionId, currentUser.getId());
    return ResponseEntity.ok(isBookmarked);
  }


}
