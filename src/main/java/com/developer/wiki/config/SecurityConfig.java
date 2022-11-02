package com.developer.wiki.config;

import com.developer.wiki.oauth.CustomOAuth2UserService;
import com.developer.wiki.oauth.OAuth2SuccessHandler;
import com.developer.wiki.oauth.TokenService;
import com.developer.wiki.oauth.UserRepository;
import com.developer.wiki.oauth.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomOAuth2UserService customOAuth2UserService;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;
  private final TokenService tokenService;
  private final UserRepository userRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().disable().csrf().disable().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers( "/oauth2/authorization/**","/api/v1/oauth/**").permitAll()
            .anyRequest().authenticated();
    http.formLogin().disable();
    http.addFilterBefore(new JwtFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
  }



}