package com.developer.wiki.question.command.domain;

import com.developer.wiki.oauth.User;
import com.developer.wiki.question.util.PasswordEncrypter;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  @ManyToOne
  private User user;

  public Comment(String nickname, String password, String content, Question question) {
    this.nickname = nickname;
    this.password = password;
    this.content = content;
    this.commentRole = CommentRole.ANONYMOUS;
    this.question = question;
    this.createdAt = LocalDateTime.now();
  }

  public Comment(String content, Question question, User user) {
    this.content = content;
    this.commentRole = CommentRole.USER;
    this.question = question;
    this.createdAt = LocalDateTime.now();
    this.user = user;
  }

  public void matchPassword(String password) {
    if (!PasswordEncrypter.isMatch(password, this.password)) {
      throw new NotMatchPasswordException();
    }
  }

  public boolean checkPassword(String password) {
    if (Objects.isNull(this.password)) {
      return false;
    }
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