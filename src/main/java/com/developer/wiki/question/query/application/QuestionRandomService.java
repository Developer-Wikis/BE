package com.developer.wiki.question.query.application;

import com.developer.wiki.question.command.domain.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class RandomQuestionService {

  private final QuestionRepository questionRepository;


}
