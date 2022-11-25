package com.developer.wiki.oauth.service;

import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.common.exception.NotFoundException;
import com.developer.wiki.oauth.User;
import com.developer.wiki.oauth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    @Transactional
    public String updateUserName(String userName,Long userId){
        if(userRepository.existsByName(userName)) throw new BadRequestException("닉네임 중복입니다.");
        User user=userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("유저가 없습니다."));
        user.changeUserName(userName);
        return user.getName();
    }

}
