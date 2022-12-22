package com.developer.wiki.question.query.application;

import com.developer.wiki.oauth.User;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionSearchRepository;
import com.developer.wiki.question.util.QuestionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionRandomService {

  private final QuestionSearchRepository questionSearchRepository;

  public Slice<RandomQuestionResponse> findRandomPage(Pageable pageable, String mainCategory,
      List<String> subCategory, User currentUser) {
    Slice<Question> questions = questionSearchRepository.findRandomBy(pageable, mainCategory,
        subCategory);
    return questions.map(QuestionConverter::ofRandom);
  }
}
