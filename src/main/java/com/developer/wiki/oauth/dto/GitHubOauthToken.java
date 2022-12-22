package com.developer.wiki.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GitHubOauthToken {
    private String access_token;
    private String scope;
    private String token_type;
}
