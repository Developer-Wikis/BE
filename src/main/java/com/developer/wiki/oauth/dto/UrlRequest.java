package com.developer.wiki.oauth.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UrlRequest {
    @NotBlank(message = "Code값이 없습니다.")
    String code;

    @NotBlank(message = "redirectUrl값이 없습니다.")
    String redirectUrl;
}
