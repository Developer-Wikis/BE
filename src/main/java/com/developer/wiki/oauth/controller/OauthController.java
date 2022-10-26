package com.developer.wiki.oauth.controller;

import com.developer.wiki.oauth.dto.GoogleResponseDto;
import com.developer.wiki.oauth.dto.UrlRequest;
import com.developer.wiki.oauth.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/oauth")
public class OauthController {
    private  final OauthService oauthService;
    @PostMapping("")
    public ResponseEntity<GoogleResponseDto> getGoogleUserInfo(@RequestBody @Valid UrlRequest urlRequest) throws IOException {
        System.out.println(">> 소셜 로그인 API 서버로부터 받은 code :"+ urlRequest.getCode());
        System.out.println(">> 소셜 로그인 API 서버로부터 받은 url :"+ urlRequest.getRedirectUrl());
        GoogleResponseDto GoogleUser = oauthService.oAuthLogin(urlRequest.getCode(),urlRequest.getRedirectUrl());
        return new ResponseEntity<>(GoogleUser, HttpStatus.OK);
    }
    @GetMapping("/google/url")
    public void getGoogleUrl(@RequestParam String url, HttpServletResponse response) throws IOException {
        System.out.println("asdasdasd"+url);
        String redirectUrl=oauthService.googleUrl(url);
        response.sendRedirect(redirectUrl);
    }
}
