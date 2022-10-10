package com.developer.wiki.question.command.domain;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface QuestionSearchRepository {

  Slice<Question> findSliceBy(Pageable pageable, String mainCategory, List<String> category);
}
