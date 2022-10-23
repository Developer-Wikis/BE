package com.developer.wiki.oauth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class Token {
    private String jwtToken;
    private String refreshToken;

    public Token(String token, String refreshToken) {
        this.jwtToken = token;
        this.refreshToken = refreshToken;
    }
}
