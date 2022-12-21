package com.developer.wiki.oauth.controller;

import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.oauth.dto.GitHubOauthToken;
import com.developer.wiki.oauth.dto.GoogleOAuthToken;
import com.developer.wiki.oauth.dto.GoogleResponseDto;
import com.developer.wiki.oauth.service.OauthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/oauth")
public class OauthController {
    private  final OauthService oauthService;
    private final ObjectMapper objectMapper;
    @Value("${custom.github.client-id}")
    private  String GITHUB_CLIENT_ID;

    @Value("${custom.github.client-secret}")
    private  String GITHUB_CLIENT_SECRET;

    @GetMapping()
    public String Code(@RequestParam(value = "code")String code) throws JsonProcessingException {
        //순서
        // 1.코드와 리다이렉트 주소가 온다.
        // 2.받은 정보로 토큰을 발급 받는다.
        // 3. 토큰으로 사용자 정보를 요청한다.
        System.out.println("코드 값임당 "+code);
        String GOOGLE_TOKEN_REQUEST_URL="https://github.com/login/oauth/access_token";
        RestTemplate restTemplate=new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        // create headers
        HttpHeaders headers1 = new HttpHeaders();
// set `content-type` header
        headers1.setContentType(MediaType.APPLICATION_JSON);
// set `accept` header
        headers1.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        params.put("code", code);
        params.put("client_id", GITHUB_CLIENT_ID);
        params.put("client_secret", GITHUB_CLIENT_SECRET);
        params.put("redirect_uri", "http://localhost:8080/api/v1/oauth");
        params.put("scope","user");
        ResponseEntity<String> responseEntity;
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(params, headers1);
        try {
            responseEntity=restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL,entity,String.class);
            System.out.println("엑세스 토큰 임당 "+responseEntity.getBody());
        }catch (RestClientException e){
            e.printStackTrace();
            throw new BadRequestException(String.format("인가코드로 구글의 AccessToken을 발급하지 못했습니다. code : %s, redirectUrl : %s,  오류 내용 : %s",code,"redirectUrl",e.getMessage()));
        }
        GitHubOauthToken tokenTest=getAccessToken(responseEntity);
        System.out.println("토큰 테스트 값 :"+tokenTest.getAccess_token());
        String GOOGLE_USERINFO_REQUEST_URL="https://api.github.com/user";
        //header에 accessToken을 담는다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+tokenTest.getAccess_token());

        //HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 된다.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        try {
            ResponseEntity<String> response=restTemplate.exchange(GOOGLE_USERINFO_REQUEST_URL, HttpMethod.GET,request,String.class);
            System.out.println("response.getBody() = " + response.getBody());
            return response.getBody();
        }catch (RestClientException e){
            throw  new BadRequestException("구글 AccessToken을 으로 사용자 정보를 가져오지 못했습니다.");
        }
    }
    private GitHubOauthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        System.out.println("response.getBody() = " + response.getBody());
        GitHubOauthToken gitHubOauthToken= objectMapper.readValue(response.getBody(),GitHubOauthToken.class);
        return gitHubOauthToken;
    }
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
    @GetMapping("/github/userinfo")
    public ResponseEntity<String> getGitHubUserInfo(@RequestParam("code") String code,@RequestParam("redirectUrl")String redirectUrl) throws IOException {
        System.out.println(">> 소셜 로그인 API 서버로부터 받은 code :"+ code);
        System.out.println(">> 소셜 로그인 API 서버로부터 받은 url :"+ redirectUrl);
        //GoogleResponseDto GoogleUser = oauthService.oAuthLogin(code,redirectUrl);
        return new ResponseEntity<>(code, HttpStatus.OK);
    }
}
