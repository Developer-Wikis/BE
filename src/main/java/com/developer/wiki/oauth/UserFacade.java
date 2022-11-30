package com.developer.wiki.oauth;

import com.developer.wiki.bookmark.BookmarkService;
import com.developer.wiki.oauth.dto.UserResponseDto;
import com.developer.wiki.question.query.application.CommentSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final BookmarkService bookmarkService;
    private final CommentSummaryService commentSummaryService;

    public UserResponseDto getUserInfo(User currentUser){
        var  user=currentUser;
        var myCommentSize=commentSummaryService.getMyCommentSize(user.getId());
        var myBookMarkSize=bookmarkService.getMyBookMarkSize(user.getId());
        var userResponseDto=new UserResponseDto(user,myCommentSize,myBookMarkSize);
        return userResponseDto;
    }

}
