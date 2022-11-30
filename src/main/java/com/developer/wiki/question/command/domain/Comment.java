package com.developer.wiki.question.command.domain;

import com.developer.wiki.question.util.PasswordEncrypter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "password")
  private String password;

  @Column(name = "content")
  private String content;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private CommentRole commentRole;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;
  @Column(name = "user_id")
  private Long userId = null;

  public Comment(String nickname, String password, String content, Question question) {
    this.nickname = nickname;
    this.password = password;
    this.content = content;
    this.commentRole = CommentRole.ANONYMOUS;
    this.question = question;
    this.createdAt = LocalDateTime.now();
    this.userId = null;
  }

  public Comment(String nickname, String password, String content, Question question, Long userId) {
    this.nickname = nickname;
    this.password = password;
    this.content = content;
    this.commentRole = CommentRole.USER;
    this.question = question;
    this.createdAt = LocalDateTime.now();
    this.userId = userId;
  }

  public void matchPassword(String password) {
    if (!PasswordEncrypter.isMatch(password, this.password)) {
      throw new NotMatchPasswordException();
    }
  }

  public boolean checkPassword(String password) {
    if (!PasswordEncrypter.isMatch(password, this.password)) {
      return false;
    }
    return true;
  }

  public void changePassword(String password) {

    this.password = PasswordEncrypter.encrypt(password);
  }

  public void changeContent(String content) {
    this.content = content;
  }

  public Long getUserId() {
    return userId;
  }
}