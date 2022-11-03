package com.developer.wiki.oauth.jwt;

import com.developer.wiki.common.exception.NotFoundException;
import com.developer.wiki.oauth.TokenService;
import com.developer.wiki.oauth.User;
import com.developer.wiki.oauth.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends GenericFilterBean {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = ((HttpServletRequest)request).getHeader("Auth");
        if (token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getUid(token);
            System.out.println("Jwt 필터에서 찾느다 실시!!!");
            User user=userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("유저가 없습니다"));
            Authentication auth = getAuthentication(user);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        else{
            log.info("헤더에 토큰이 없습니다.");
        }
        chain.doFilter(request, response);
    }

    public Authentication getAuthentication(User user) {
        return new UsernamePasswordAuthenticationToken(user, "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {

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
            Map<String, Object> values = jwtUtil.validateToken(tokenStr);

            return values;
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
