package com.developer.wiki.question.command.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "tail_question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TailQuestion {

  @Id
  @GeneratedValue
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
