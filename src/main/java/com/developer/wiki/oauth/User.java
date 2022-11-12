package com.developer.wiki.oauth;

import antlr.StringUtils;
import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.oauth.dto.UserResponseDto;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue
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

    public User(String name, String email,String profileUrl){
        if(name.isEmpty() || email.isEmpty() || profileUrl.isEmpty()){
            throw new BadRequestException("email or name 값이 없습니다.");
        }
        this.name=name;
        this.email=email;
        this.profileUrl=profileUrl;
        this.role=Role.USER;
    }

    public UserResponseDto toDto(){
        return  new UserResponseDto(this);
    }
}
