package com.developer.wiki.oauth.service;

import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.common.exception.NotFoundException;
import com.developer.wiki.oauth.User;
import com.developer.wiki.oauth.UserRepository;
import com.developer.wiki.oauth.dto.CommentDto;
import com.developer.wiki.oauth.util.AwsService;
import com.developer.wiki.oauth.util.ExtType;
import com.developer.wiki.question.command.domain.Comment;
import com.developer.wiki.question.command.domain.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AwsService awsService;
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

    @Transactional
    public String updateUserProfile(MultipartFile file, Long userId) throws IOException {
        User user=userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("유저가 없습니다."));
        String originalName=file.getOriginalFilename();
        String[] name=originalName.split("\\.");
        final String ext = name[1];
        final String saveFileName = getUuid() +"."+ ExtType.validType(ext);
        System.out.println(saveFileName);
        var newUrl=awsService.upload(saveFileName,file);
        user.changeUserProfileUrl(newUrl);
        return newUrl;
    }
    private static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    @Transactional
    public Page<CommentDto> findCommentList(Long userId, Pageable pageable){
       List<Comment> list=commentRepository.findByUserIdOrderByCreatedAtDesc(userId);
       List<CommentDto> commentDtoList=list.stream()
               .map(comment -> new CommentDto(
                       comment.getId()
                       ,comment.getContent()
                       ,comment.getCreatedAt()
                       ,comment.getQuestion().getId()
                       ,comment.getQuestion().getTitle()
                       ,comment.getQuestion().getMainCategory().name()
                       ,comment.getQuestion().getSubCategory().getCategory()
                       )
               )
               .collect(Collectors.toList());
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), commentDtoList.size());
       Page<CommentDto> pageComment=new PageImpl<>(commentDtoList.subList(start,end),pageable,commentDtoList.size());
       return pageComment;
    }

    @Transactional
    public Boolean updateUserDefaultProfile(Long userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("유저가 없습니다."));
        user.changeUserDefaultProfileUrl();
        return true;
    }
}
