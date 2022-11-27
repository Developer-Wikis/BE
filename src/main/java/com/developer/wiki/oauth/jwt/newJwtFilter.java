package com.developer.wiki.oauth.jwt;

import com.developer.wiki.oauth.User;
import com.developer.wiki.oauth.UserRepository;
import com.developer.wiki.oauth.exception.AccessTokenException;
import com.developer.wiki.oauth.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


@Log4j2
@RequiredArgsConstructor
public class newJwtFilter extends OncePerRequestFilter {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    log.info("Token Check Filter..........................");
    try {
      String path = request.getRequestURI();
      if (path.startsWith("/api/v1/oauth")) {
        log.info("넘어간다~~~~");
        filterChain.doFilter(request, response);
        return;
      }

      String email = validateAccessToken(request);
      if (Objects.isNull(email)) {
        filterChain.doFilter(request, response);
        return;
      }
      log.info("email: " + email);
      User user = userRepository.findByEmail(email)
          .orElseThrow(() -> new AccessTokenException(AccessTokenException.TOKEN_ERROR.NOTFOUND));
      Authentication auth = getAuthentication(user);
      SecurityContextHolder.getContext().setAuthentication(auth);
      filterChain.doFilter(request, response);
    } catch (AccessTokenException accessTokenException) {
      accessTokenException.sendResponseError(response);
    }
  }

  private Authentication getAuthentication(User user) {
    return new UsernamePasswordAuthenticationToken(user, "",
        Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
  }


  private String validateAccessToken(HttpServletRequest request) throws AccessTokenException {

    String headerStr = request.getHeader("Authorization");

    if (headerStr == null) {
      System.out.println("222222" + headerStr);
      return null;
    }
    if(headerStr.length()<=7){
      System.out.println("익명 사용자다!!!!");
      return null;
    }
    //Bearer 생략
    String tokenType = headerStr.substring(0, 6);
    String tokenStr = headerStr.substring(7);


    if (!tokenType.equalsIgnoreCase("Bearer")) {
      throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
    }

    try {
      //Map<String, Object> values = jwtUtil.validateToken(tokenStr);
      Map<String, Object> email = jwtUtil.validateToken(tokenStr);
      return (String) email.get("email");
    } catch (MalformedJwtException malformedJwtException) {
      log.error("MalformedJwtException----------------------");
      throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
    } catch (SignatureException signatureException) {
      log.error("SignatureException----------------------");
      throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
    } catch (ExpiredJwtException expiredJwtException) {
      log.error("ExpiredJwtException----------------------");
      throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
    }
  }
}
