package com.developer.wiki.oauth.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
public class NicknameDto {
    @NotBlank(message = "닉네임은 null일 수 없습니다.")
    @Pattern(regexp="^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,10}$", message = "닉네임 조건 불일치")
    private String username;
    public NicknameDto(String userName){
        this.username=userName;
    }
}
