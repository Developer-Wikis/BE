package com.developer.wiki.oauth.util;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class JwtUtil {
    private final long  tokenPeriod = 60 *60 * 24 * 7* 1000L; //1주
    private  final long refreshPeriod = 60 * 60 * 24 * 7 * 1000L*3; //3주

    @Value("${custom.jwt.secretKey}")
    private String key;

    public String generateToken(Map<String, Object> valueMap){
        Date now = new Date();
            log.info("generateAccessToken...");
            //accesstoken이라면
            Map<String, Object> headers = new HashMap<>();
            headers.put("typ","JWT");
            headers.put("alg","HS256");

            //payload 부분 설정
            Map<String, Object> payloads = new HashMap<>();
            payloads.putAll(valueMap);
            String jwtStr = Jwts.builder()
                    .setHeader(headers)
                    .setClaims(payloads)
                    .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                    .setExpiration(new Date(now.getTime() + Duration.ofMinutes(1).toMillis()))
                    .signWith(SignatureAlgorithm.HS256, key.getBytes())
                    .compact();
            return jwtStr;
    }

    public String generateRefreshToken(){
        Date now = new Date();
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ","JWT");
        headers.put("alg","HS256");
        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        String jwtStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(10).toMillis()))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();
        return jwtStr;

    }


    public Map<String, Object> validateToken(String token)throws JwtException {

        Map<String, Object> claim = null;

        claim = Jwts.parser()
                .setSigningKey(key.getBytes()) // Set Key
                .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
                .getBody();
        return claim;
    }

}
