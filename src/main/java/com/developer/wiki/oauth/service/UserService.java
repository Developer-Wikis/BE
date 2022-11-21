package com.developer.wiki.oauth.service;

import com.developer.wiki.oauth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

}
