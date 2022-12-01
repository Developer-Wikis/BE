package com.developer.wiki.oauth.controller;


import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.common.exception.UnAuthorizedException;
import com.developer.wiki.oauth.User;
import com.developer.wiki.oauth.UserFacade;
import com.developer.wiki.oauth.dto.CommentDto;
import com.developer.wiki.oauth.dto.ImageDto;
import com.developer.wiki.oauth.dto.NicknameDto;
import com.developer.wiki.oauth.dto.UserResponseDto;
import com.developer.wiki.oauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final UserFacade userFacade;

    @GetMapping
    public ResponseEntity<UserResponseDto> getUserInfo(@AuthenticationPrincipal User currentUser){
        //북마크 갯수, 답글 갯수 도 내려주기
        var userInfo=userFacade.getUserInfo(currentUser);
        return ResponseEntity.ok().body(userInfo);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal User currentUser, @PathVariable(name = "userId")Long userid){
        if(!currentUser.getId().equals(userid)) throw new BadRequestException("Not Match Userid");
        userService.deleteUser(currentUser.getId());
        return ResponseEntity.ok("delete");
    }



    @PostMapping("/username/{userId}")
    public ResponseEntity<NicknameDto> changeUserName(@AuthenticationPrincipal User currentUser,
                                                      @PathVariable(name = "userId")Long userId,
                                                      @RequestBody @Valid NicknameDto nicknameDto){
        if(!currentUser.getId().equals(userId)) throw new BadRequestException("Not Match Userid");
        String newUserName=userService.updateUserName(nicknameDto.getUsername(),userId);
        return ResponseEntity.ok(new NicknameDto(newUserName));
    }

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageDto> changeImg(@AuthenticationPrincipal User currentUser, @RequestParam("image") MultipartFile file, @PathVariable Long userId) throws IOException {
        if(!currentUser.getId().equals(userId)) throw new BadRequestException("Not Match Userid");
        if(file.isEmpty()) throw new BadRequestException("파일은 Null이 될수 없습니다.");
        String url=userService.updateUserProfile(file, userId);
        return ResponseEntity.ok(new ImageDto(url));
    }

    @GetMapping("/comment")
    public ResponseEntity<Page<CommentDto>> getCommentList(@AuthenticationPrincipal User currentUser, Pageable pageable){
        if (Objects.isNull(currentUser)) {
            throw new UnAuthorizedException("토큰이 필요합니다.");
        }
        var list=userService.findCommentList(currentUser.getId(),pageable);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/profile/default/{userId}")
    public ResponseEntity<Boolean> changeDefaultProfile(@AuthenticationPrincipal User currentUser,
                                                      @PathVariable(name = "userId")Long userId){
        if(!currentUser.getId().equals(userId)) throw new BadRequestException("Not Match Userid");
        return ResponseEntity.ok(userService.updateUserDefaultProfile(userId));
    }
}
