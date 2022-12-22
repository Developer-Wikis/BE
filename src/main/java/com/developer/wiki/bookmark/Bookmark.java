package com.developer.wiki.bookmark;

import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.question.command.domain.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bookmark")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id")
  private Question question;

  @Column(name = "user_id")
  private Long userId;

  public Bookmark(Long userId, Question question) {
    setUserId(userId);
    setQuestion(question);
  }

  private void setUserId(Long userId) {
    if (Objects.isNull(userId) || userId <= 0) {
      throw new BadRequestException("잘못된 사용자 아이디가 입력되었습니다.");
    }
    this.userId = userId;
  }

  private void setQuestion(Question question) {
    if (Objects.isNull(question)) {
      throw new BadRequestException("코스로 빈 값을 받을 수 없습니다.");
    }
    this.question = question;
  }
}