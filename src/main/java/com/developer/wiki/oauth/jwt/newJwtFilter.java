package com.developer.wiki.oauth.jwt;

import com.developer.wiki.common.exception.NotFoundException;
import com.developer.wiki.oauth.TokenService;
import com.developer.wiki.oauth.User;
import com.developer.wiki.oauth.UserRepository;
import com.developer.wiki.oauth.exception.AccessTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


@Log4j2
@RequiredArgsConstructor
public class newJwtFilter  extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Token Check Filter..........................");
        try{
            String path = request.getRequestURI();
            if ((path.startsWith("/api/v1/questions")&&request.getMethod().equals("GET"))||path.startsWith("/api/v1/oauth")||path.startsWith("/api/v1/questions/")) {
                log.info("넘어간다~~~~");
                filterChain.doFilter(request, response);
                return;
            }


            String email = validateAccessToken(request);
            log.info("email: " + email);
            User user=userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("유저가 없습니다"));
            Authentication auth = getAuthentication(user);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request,response);
        }catch(AccessTokenException accessTokenException){
            accessTokenException.sendResponseError(response);
        }
    }
    private Authentication getAuthentication(User user) {
        return new UsernamePasswordAuthenticationToken(user, "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }


    private String validateAccessToken(HttpServletRequest request) throws AccessTokenException {

        String headerStr = request.getHeader("Authorization");

        if(headerStr == null  || headerStr.length() < 8){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
        }

        //Bearer 생략
        String tokenType = headerStr.substring(0,6);
        String tokenStr =  headerStr.substring(7);

        if(tokenType.equalsIgnoreCase("Bearer") == false){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
        }

        try{
            //Map<String, Object> values = jwtUtil.validateToken(tokenStr);
            String email=tokenService.getUid(tokenStr);

            return email;
        }catch(MalformedJwtException malformedJwtException){
            log.error("MalformedJwtException----------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
        }catch(SignatureException signatureException){
            log.error("SignatureException----------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
        }catch(ExpiredJwtException expiredJwtException){
            log.error("ExpiredJwtException----------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
        }
    }
}
