package com.developer.wiki.oauth.controller;


import com.developer.wiki.oauth.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal User currentUser){
        return ResponseEntity.ok().body(currentUser);
    }
}
