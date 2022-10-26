package com.developer.wiki.oauth;

import com.developer.wiki.common.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(unique = true,name="email")
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User(String name, String email){
        if(name.isEmpty() || email.isEmpty()){
            throw new BadRequestException("email or name 값이 없습니다.");
        }
        this.name=name;
        this.email=email;
        this.role=Role.USER;
    }
}
