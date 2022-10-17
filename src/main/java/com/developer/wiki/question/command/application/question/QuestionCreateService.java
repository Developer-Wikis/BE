package com.developer.wiki.question.command.application.question;

import com.developer.wiki.question.command.application.dto.CreateQuestionRequest;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionRepository;
import com.developer.wiki.question.command.domain.TailQuestion;
import com.developer.wiki.question.command.domain.TailQuestionRepository;
import com.developer.wiki.question.util.QuestionConverter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionCreateService {

  private final QuestionRepository questionRepository;
  private final TailQuestionRepository tailQuestionRepository;

  public Long create(CreateQuestionRequest createQuestionRequest) {
    Question question = QuestionConverter.toQuestion(createQuestionRequest);
    List<TailQuestion> tailQuestions = createQuestionRequest.getTailQuestions().stream()
        .map(t -> new TailQuestion(t, question)).collect(Collectors.toList());
    tailQuestionRepository.saveAll(tailQuestions);
    return questionRepository.save(question).getId();
  }
}
