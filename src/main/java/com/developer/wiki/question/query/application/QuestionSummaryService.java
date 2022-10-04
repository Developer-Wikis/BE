package com.developer.wiki.question.query.application;

import com.developer.wiki.question.command.domain.Category;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import com.developer.wiki.question.command.domain.QuestionSearchRepository;
import com.developer.wiki.question.util.QuestionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionSummaryService {

  private final QuestionRepository questionRepository;
  private final QuestionSearchRepository questionSearchRepository;

  public Slice<SummaryQuestionResponse> findSlice(Pageable pageable, List<String> category) {
    Slice<Question> questions = questionSearchRepository.findSliceBy(pageable, category);
    return questions.map(QuestionConverter::ofSummary);
  }
}
