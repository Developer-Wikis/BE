package com.developer.wiki.question.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TailQuestionRepository extends JpaRepository<TailQuestion, Long> {

}

