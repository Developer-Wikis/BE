package com.developer.wiki.oauth.dto;

import com.developer.wiki.oauth.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String profileUrl;
    private String role;
    private Long commentSize;
    private Long bookmarkSize;


    public UserResponseDto(User user,Long commentSize, Long bookMarkSize){
        this.id= user.getId();
        this.username= user.getName();
        this.email= user.getEmail();
        this.profileUrl= user.getProfileUrl();
        this.role=user.getRole().name();
        this.commentSize=commentSize;
        this.bookmarkSize=bookMarkSize;
    }
}
