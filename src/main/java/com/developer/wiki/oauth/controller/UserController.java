package com.developer.wiki.oauth.controller;


import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.oauth.User;
import com.developer.wiki.oauth.dto.UserResponseDto;
import com.developer.wiki.oauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDto> getUserInfo(@AuthenticationPrincipal User currentUser){
        return ResponseEntity.ok().body(currentUser.toDto());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUSer(@AuthenticationPrincipal User currentUser, @PathVariable(name = "userId")Long userid){
        if(!currentUser.getId().equals(userid)) throw new BadRequestException("Not Match Userid");
        userService.deleteUser(currentUser.getId());
        return ResponseEntity.ok("delete");
    }
}
