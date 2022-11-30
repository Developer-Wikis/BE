package com.developer.wiki.config;

import com.developer.wiki.oauth.CustomOAuth2UserService;
import com.developer.wiki.oauth.OAuth2SuccessHandler;
import com.developer.wiki.oauth.UserRepository;
import com.developer.wiki.oauth.jwt.RefreshTokenFilter;
import com.developer.wiki.oauth.jwt.newJwtFilter;
import com.developer.wiki.oauth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomOAuth2UserService customOAuth2UserService;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().disable().csrf().disable().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.cors().configurationSource(corsConfigurationSource())
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
            .antMatchers( HttpMethod.GET,"/api/v1/questions/**").permitAll()
            .antMatchers( HttpMethod.POST,"/api/v1/questions/**").permitAll()
            .antMatchers( "/api/v1/oauth/**").permitAll()
            .anyRequest().permitAll();
    http.formLogin().disable()
            .oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService)
            .and()
            .successHandler(oAuth2SuccessHandler);
    http.addFilterBefore(new newJwtFilter(userRepository,jwtUtil), UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(new RefreshTokenFilter("/api/v1/refreshToken", jwtUtil,userRepository),
            newJwtFilter.class);
  }
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE","OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}