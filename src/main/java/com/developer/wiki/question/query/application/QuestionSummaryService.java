package com.developer.wiki.question.query.application;

import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import com.developer.wiki.question.util.QuestionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionSummaryService {

  private final QuestionRepository questionRepository;

  public Slice<SummaryQuestionResponse> findSlice(Pageable pageable) {
    Slice<Question> questions = questionRepository.findSliceBy(pageable);
    return questions.map(QuestionConverter::ofSummary);
  }
}
