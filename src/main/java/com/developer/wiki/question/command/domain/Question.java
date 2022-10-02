package com.developer.wiki.question.command.domain;

import com.developer.wiki.question.util.PasswordEncrypter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

@Entity
@DynamicUpdate
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
  private Category category;

  @Column(name = "view_count")
  private Long viewCount;

  @Formula("(select count(1) from comment c where c.question_id=id)")
  private Long commentCount;

  @ElementCollection(fetch = FetchType.LAZY)
  private Set<String> additionQuestions = new LinkedHashSet<>();

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question")
  private List<Comment> comments = new ArrayList<>();

  public Question(String title, String nickname, String password, Category category,
      List<String> additionQuestions) {
    this.title = title;
    this.nickname = nickname;
    this.password = password;
    this.category = category;
    this.viewCount = 0L;
    this.additionQuestions = new LinkedHashSet<>(additionQuestions);
    this.createdAt = LocalDateTime.now();
  }

  public void addViewCount() {
    this.viewCount += 1;
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

  public void changeTitle(String title) {
    this.title = title;
  }

  public void changeCategory(Category category) {
    this.category = category;
  }

  public void changeAdditionQuestions(List<String> additionQuestions) {
    this.additionQuestions = new LinkedHashSet<>(additionQuestions);
  }
}