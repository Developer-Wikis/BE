package com.developer.wiki.oauth.service;

import com.developer.wiki.oauth.TokenService;
import com.developer.wiki.oauth.User;
import com.developer.wiki.oauth.UserRepository;
import com.developer.wiki.oauth.dto.GoogleOAuthToken;
import com.developer.wiki.oauth.dto.GoogleResponseDto;
import com.developer.wiki.oauth.dto.GoogleUser;
import com.developer.wiki.oauth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final GoogleOauthService googleOauthService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Transactional
    public GoogleResponseDto oAuthLogin(String code, String redirectUrl) throws IOException {
        ResponseEntity<String> accessTokenResponse= googleOauthService.requestAccessToken(code, redirectUrl);
        GoogleOAuthToken oAuthToken=googleOauthService.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse=googleOauthService.requestUserInfo(oAuthToken);
        GoogleUser googleUser= googleOauthService.getUserInfo(userInfoResponse);
        User user =userRepository.findByEmail(googleUser.getEmail()).orElse(null);
        if(user==null) {
            User newUser = new User(
                    googleUser.getName(),
                    googleUser.getEmail(),
                    googleUser.getPicture());
            user=newUser;
            userRepository.save(newUser);
        }
        String accessToken=jwtUtil.generateToken(Map.of("email",user.getEmail()));
        String refreshToken=jwtUtil.generateRefreshToken();
        user.updateRefreshToken(refreshToken);
        GoogleResponseDto googleResponseDto=new GoogleResponseDto(user,accessToken,refreshToken);
        return googleResponseDto;
    }

    public String googleUrl(String url){
        return googleOauthService.googleInitUrl(url);
    }
}
