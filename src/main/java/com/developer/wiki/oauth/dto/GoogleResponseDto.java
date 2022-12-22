package com.developer.wiki.oauth.dto;

import com.developer.wiki.oauth.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleResponseDto {
    private Long id;
    private String name;
    private String email;
    private String profileUrl;
    private String role;
    private String accessToken;
    private String refreshToken;

    @Builder
    public GoogleResponseDto(User user, String accessToken, String refreshToken){
        this.id= user.getId();
        this.name= user.getName();
        this.email= user.getEmail();
        this.profileUrl= user.getProfileUrl();
        this.role=user.getRole().name();
        this.accessToken=accessToken;
        this.refreshToken=refreshToken;
    }
}
