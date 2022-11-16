package com.developer.wiki.oauth.controller;


import com.developer.wiki.oauth.User;
import com.developer.wiki.oauth.dto.UserResponseDto;
import com.developer.wiki.oauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDto> getUserInfo(@AuthenticationPrincipal User currentUser){
        return ResponseEntity.ok().body(currentUser.toDto());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUSer(@AuthenticationPrincipal User currentUser){
        userService.deleteUser(currentUser.getId());
        return ResponseEntity.ok("delete");
    }
}
