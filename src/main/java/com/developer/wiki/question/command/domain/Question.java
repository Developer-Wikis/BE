package com.developer.wiki.question.command.domain;

import com.developer.wiki.question.command.PasswordEncrypter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "password")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "category")
  private Category category = Category.없음;

  @ElementCollection(fetch = FetchType.LAZY)
  private List<String> additionQuestions = new ArrayList<>();

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question")
  private List<Comment> histories = new ArrayList<>();

  public Question(String title, String nickname, String password, Category category,
      List<String> additionQuestions) {
    this.title = title;
    this.nickname = nickname;
    this.password = password;
    this.category = category;
    this.additionQuestions = additionQuestions;
    this.createdAt = LocalDateTime.now();
  }

  public void matchPassword(String password) {
    if (!PasswordEncrypter.isMatch(password, this.password)) {
      throw new NotMatchPasswordException();
    }
  }
}