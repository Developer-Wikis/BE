package com.developer.wiki.oauth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private String email;
    private String name;

    @Builder
    public UserDto(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
