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


    @Builder
    public UserResponseDto(User user){
        this.id= user.getId();
        this.username= user.getName();
        this.email= user.getEmail();
        this.profileUrl= user.getProfileUrl();
        this.role=user.getRole().name();
    }
}
