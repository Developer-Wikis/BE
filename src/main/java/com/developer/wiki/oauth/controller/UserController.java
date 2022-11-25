package com.developer.wiki.oauth.controller;


import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.oauth.User;
import com.developer.wiki.oauth.dto.ImageDto;
import com.developer.wiki.oauth.dto.NicknameDto;
import com.developer.wiki.oauth.dto.UserResponseDto;
import com.developer.wiki.oauth.service.UserService;
import com.developer.wiki.oauth.util.AwsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final AwsService awsService;

    @GetMapping
    public ResponseEntity<UserResponseDto> getUserInfo(@AuthenticationPrincipal User currentUser){
        return ResponseEntity.ok().body(currentUser.toDto());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal User currentUser, @PathVariable(name = "userId")Long userid){
        if(!currentUser.getId().equals(userid)) throw new BadRequestException("Not Match Userid");
        userService.deleteUser(currentUser.getId());
        return ResponseEntity.ok("delete");
    }



    @PostMapping("/nickname/{userId}")
    public ResponseEntity<NicknameDto> changeUserName(@AuthenticationPrincipal User currentUser,
                                                      @PathVariable(name = "userId")Long userId,
                                                      @RequestBody @Valid NicknameDto nicknameDto){
        if(!currentUser.getId().equals(userId)) throw new BadRequestException("Not Match Userid");
        String newUserName=userService.updateUserName(nicknameDto.getUserName(),userId);
        return ResponseEntity.ok(new NicknameDto(newUserName));
    }

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageDto> changeImg(@AuthenticationPrincipal User currentUser, @RequestParam("image") MultipartFile file, @PathVariable Long userId) throws IOException {
        if(!currentUser.getId().equals(userId)) throw new BadRequestException("Not Match Userid");
        if(file.isEmpty()) throw new BadRequestException("파일은 Null이 될수 없습니다.");
        String url=userService.updateUserProfile(file, userId);
        return ResponseEntity.ok(new ImageDto(url));
    }

}
