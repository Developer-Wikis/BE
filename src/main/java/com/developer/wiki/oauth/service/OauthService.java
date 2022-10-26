package com.developer.wiki.oauth.service;

import com.developer.wiki.oauth.*;
import com.developer.wiki.oauth.dto.GoogleOAuthToken;
import com.developer.wiki.oauth.dto.GoogleResponseDto;
import com.developer.wiki.oauth.dto.GoogleUser;
import com.developer.wiki.oauth.util.UrlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final GoogleOauthService googleOauthService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    public GoogleResponseDto oAuthLogin(String code, String redirectUrl) throws IOException {
        ResponseEntity<String> accessTokenResponse= googleOauthService.requestAccessToken(code, UrlConverter.of(redirectUrl));
        GoogleOAuthToken oAuthToken=googleOauthService.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse=googleOauthService.requestUserInfo(oAuthToken);
        GoogleUser googleUser= googleOauthService.getUserInfo(userInfoResponse);
        User user =userRepository.findByEmail(googleUser.getEmail()).orElse(null);
        if(user==null) {
            User user1 = new User(
                    googleUser.getName(),
                    googleUser.getEmail());
            userRepository.save(user1);
        }
        Token token = tokenService.generateToken(googleUser.getEmail(), "ROLE_USER");
        GoogleResponseDto googleResponseDto=new GoogleResponseDto(googleUser.getName(),googleUser.getEmail(),token.getJwtToken(),token.getRefreshToken());
        return googleResponseDto;
    }

    public String googleUrl(String url){
        return googleOauthService.googleInitUrl(url);
    }
}
