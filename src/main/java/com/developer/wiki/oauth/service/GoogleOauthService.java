package com.developer.wiki.oauth.service;

import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.oauth.dto.GoogleOAuthToken;
import com.developer.wiki.oauth.dto.GoogleUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoogleOauthService {
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;
    @Value("${custom.google.client-id}")
    private String GOOGLE_SNS_CLIENT_ID;

    @Value("${custom.google.client-secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;

    @Value("${custom.google.scope}")
    private String GOOGLE_SCOPE;
    private String REDIRECT_URL;


    public String googleInitUrl(String url) {
        StringBuilder stringBuilder=new StringBuilder();
        REDIRECT_URL=url;
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", url);
        params.put("response_type", "code");
        params.put("scope", GOOGLE_SCOPE);

        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));
        return stringBuilder
                .append("https://accounts.google.com/")
                .append("o/oauth2/v2/auth?")
                .append(paramStr)
                .toString();
    }

    public ResponseEntity<String> requestAccessToken(String code,String redirectUrl) {
        String GOOGLE_TOKEN_REQUEST_URL="https://oauth2.googleapis.com/token";
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", redirectUrl);
        params.put("grant_type", "authorization_code");
        try {
            ResponseEntity<String> responseEntity=restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL,
                    params,String.class);
            return responseEntity;
        }catch (RestClientException e){
            e.printStackTrace();
            throw new BadRequestException(String.format("인가코드로 구글의 AccessToken을 발급하지 못했습니다. code : %s, redirectUrl : %s,  오류 내용 : %s",code,redirectUrl,e.getMessage()));
        }
    }

    public GoogleOAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        System.out.println("response.getBody() = " + response.getBody());
        GoogleOAuthToken googleOAuthToken= objectMapper.readValue(response.getBody(),GoogleOAuthToken.class);
        return googleOAuthToken;
    }

    public ResponseEntity<String> requestUserInfo(GoogleOAuthToken oAuthToken) {
        String GOOGLE_USERINFO_REQUEST_URL="https://www.googleapis.com/oauth2/v1/userinfo";

        //header에 accessToken을 담는다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+oAuthToken.getAccess_token());

        //HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 된다.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        try {
            ResponseEntity<String> response=restTemplate.exchange(GOOGLE_USERINFO_REQUEST_URL, HttpMethod.GET,request,String.class);
            System.out.println("response.getBody() = " + response.getBody());
            return response;
        }catch (RestClientException e){
            throw  new BadRequestException("구글 AccessToken을 으로 사용자 정보를 가져오지 못했습니다.");
        }
    }

    public GoogleUser getUserInfo(ResponseEntity<String> userInfoRes) throws JsonProcessingException{
        GoogleUser googleUser=objectMapper.readValue(userInfoRes.getBody(),GoogleUser.class);
        return googleUser;
    }
}
