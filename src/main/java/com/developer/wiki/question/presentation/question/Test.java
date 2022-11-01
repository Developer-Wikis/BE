package com.developer.wiki.question.presentation.question;

import com.developer.wiki.oauth.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Test {
    @GetMapping("/test")
    public ResponseEntity<User> Test(@AuthenticationPrincipal User user){
        System.out.println(user.getEmail());
        return ResponseEntity.ok().body(user);
    }
}
