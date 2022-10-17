package com.developer.wiki.question.query.application;

import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionSearchRepository;
import com.developer.wiki.question.util.QuestionConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionRandomService {

  private final QuestionSearchRepository questionSearchRepository;

  public Slice<SummaryQuestionResponse> findRandomSlice(
      Pageable pageable, String mainCategory,
      List<String> subCategory) {
    System.out.println("pageable = " + pageable);
    Slice<Question> questions = questionSearchRepository.findRandomBy(pageable, mainCategory,
        subCategory);
    return questions.map(QuestionConverter::ofSummary);
  }
}
