package com.developer.wiki.oauth;

import com.developer.wiki.oauth.jwt.Token;
import com.developer.wiki.oauth.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        UserDto userDto = userRequestMapper.toDto(oAuth2User);

        Token token = new Token(jwtUtil.generateToken(Map.of("email",userDto.getEmail())), jwtUtil.generateRefreshToken());
        writeTokenResponse(response, token,userDto);
    }

    private void writeTokenResponse(HttpServletResponse response, Token token, UserDto userDto)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Auth", token.getJwtToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.addHeader("User",userDto.getName());
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.println(objectMapper.writeValueAsString(userDto));
        writer.flush();
    }
}
