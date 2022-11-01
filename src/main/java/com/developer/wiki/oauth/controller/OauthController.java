package com.developer.wiki.oauth.controller;

import com.developer.wiki.oauth.dto.GoogleResponseDto;
import com.developer.wiki.oauth.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/oauth")
public class OauthController {
    private  final OauthService oauthService;
    @GetMapping("/google/userinfo")
    public ResponseEntity<GoogleResponseDto> getGoogleUserInfo(@RequestParam("code") String code,@RequestParam("redirectUrl")String redirectUrl) throws IOException {
        System.out.println(">> 소셜 로그인 API 서버로부터 받은 code :"+ code);
        System.out.println(">> 소셜 로그인 API 서버로부터 받은 url :"+ redirectUrl);
        GoogleResponseDto GoogleUser = oauthService.oAuthLogin(code,redirectUrl);
        return new ResponseEntity<>(GoogleUser, HttpStatus.OK);
    }
    @GetMapping("/google")
    public ResponseEntity<String> getGoogleUrl(@RequestParam String url, HttpServletResponse response) throws IOException {
        String redirectUrl=oauthService.googleUrl(url);
        //response.sendRedirect(redirectUrl);
        return ResponseEntity.ok(redirectUrl);
    }
}
