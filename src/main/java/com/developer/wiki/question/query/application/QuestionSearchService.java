package com.developer.wiki.question.query.application;

import com.developer.wiki.oauth.User;
import com.developer.wiki.question.command.domain.QuestionSearchRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionSearchService {

  private final QuestionSearchRepository questionSearchRepository;

  public Slice<SummaryQuestionResponse> findPage(Pageable pageable,
      User currentUser, String keyword) {
    Long userId = Objects.isNull(currentUser) ? null : currentUser.getId();
    return questionSearchRepository.findPageByUserIdAndKeyword(pageable, userId, keyword);
  }
}
