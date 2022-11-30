package com.developer.wiki.question.command.domain;

import com.developer.wiki.question.query.application.SummaryQuestionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface QuestionSearchRepository {

  Page<SummaryQuestionResponse> findPageByUserId(Pageable pageable, String mainCategory, List<String> subCategory,
      Long userId);

  Page<SummaryQuestionResponse> findBookmarkByUserId(Pageable pageable, String mainCategory, List<String> subCategory,
      Long userId);

  Slice<Question> findRandomBy(Pageable pageable, String mainCategory, List<String> subCategory);
}
