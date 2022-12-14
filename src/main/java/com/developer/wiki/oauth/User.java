package com.developer.wiki.oauth;


import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.oauth.dto.UserResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(unique = true,name="email")
    private String email;

    private String profileUrl;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;


    private String refreshToken;

    public User(String name, String email,String profileUrl){
        if(name.isEmpty() || email.isEmpty() || profileUrl.isEmpty()){
            throw new BadRequestException("email or name 값이 없습니다.");
        }
        this.name=name;
        this.email=email;
        this.profileUrl=profileUrl;
        this.role=Role.USER;
    }


    public void updateRefreshToken(String token){
        this.refreshToken=token;
    }

    public void changeUserName(String newUserName){
        if(newUserName.isEmpty()) throw new BadRequestException("userName is empty");
        this.name=newUserName;
    }
    public void changeUserProfileUrl(String newUrl){
        if(newUrl.isEmpty()) throw new BadRequestException("newUrl is empty");
        this.profileUrl=newUrl;
    }

    public void changeUserDefaultProfileUrl(){
        if(!Objects.isNull(this.profileUrl)){
            this.profileUrl=null;
        }
    }
}
