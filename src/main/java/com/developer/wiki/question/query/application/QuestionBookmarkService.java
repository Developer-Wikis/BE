package com.developer.wiki.question.query.application;

import com.developer.wiki.question.command.domain.QuestionSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionBookmarkService {

  private final QuestionSearchRepository questionRepository;


  public Page<SummaryQuestionResponse> findBookmarkedList(Long userId, Pageable pageable,
      String mainCategory, List<String> subCategory) {
    return questionRepository.findBookmarkByUserId(pageable, mainCategory, subCategory, userId);
  }
}
