package com.developer.wiki.question.command.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Table(name = "tail_question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TailQuestion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "tail_question")
  private String tailQuestion;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  @Column(name = "is_approved")
  private Boolean isApproved;

  public TailQuestion(String tailQuestion, Question question) {
    this.tailQuestion = tailQuestion;
    this.question = question;
    this.isApproved = Boolean.TRUE;
  }
}
