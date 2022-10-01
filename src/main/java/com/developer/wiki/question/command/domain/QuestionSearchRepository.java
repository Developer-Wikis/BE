package com.developer.wiki.question.command.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface QuestionRepositoryCustom {

  Slice<Question> findSliceBy(Pageable pageable, Category category);
}
