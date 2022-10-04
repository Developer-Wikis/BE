package com.developer.wiki.question.command.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface QuestionSearchRepository {

  Slice<Question> findSliceBy(Pageable pageable, List<String> category);
}
