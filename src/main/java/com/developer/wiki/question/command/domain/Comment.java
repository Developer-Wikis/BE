package com.developer.wiki.question.command.domain;

import com.developer.wiki.question.util.PasswordEncrypter;
import java.time.LocalDateTime;
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

  @Column(name = "status")
  private CommentStatus commentStatus;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;
  @Column(name = "user_id")
  private Long userId;

  public Comment(String nickname, String password, String content, Question question) {
    this.nickname = nickname;
    this.password = password;
    this.content = content;
    this.commentStatus = CommentStatus.ANONYMOUS;
    this.question = question;
    this.createdAt = LocalDateTime.now();
    this.userId = null;
  }

  public Comment(String nickname, String password, String content, Question question, Long userId) {
    this.nickname = nickname;
    this.password = password;
    this.content = content;
    this.commentStatus = CommentStatus.USER;
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
}