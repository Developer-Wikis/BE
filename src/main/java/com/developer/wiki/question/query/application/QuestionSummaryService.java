package com.developer.wiki.question.query.application;

import com.developer.wiki.oauth.User;
import com.developer.wiki.question.command.domain.QuestionSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionSummaryService {

  private final QuestionSearchRepository questionSearchRepository;

  public Slice<SummaryQuestionResponse> findPage(Pageable pageable, String mainCategory,
      List<String> subCategory, User currentUser) {
    Long userId = Objects.isNull(currentUser) ? null : currentUser.getId();
    return questionSearchRepository.findPageByUserId(pageable, mainCategory,
        subCategory,userId);
  }
}
