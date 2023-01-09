package com.developer.wiki.question.command.domain;

import com.developer.wiki.bookmark.Bookmark;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(name = "question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Enumerated(EnumType.STRING)
  @Column(name = "main_category")
  private MainCategory mainCategory;

  @Enumerated(EnumType.STRING)
  @Column(name = "sub_category")
  private SubCategory subCategory;

  @Column(name = "view_count")
  private Long viewCount;

  @Formula("(select count(1) from comment c where c.question_id=id)")
  private Long commentCount;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question")
  private Set<TailQuestion> tailQuestions = new LinkedHashSet<>();

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question")
  private List<Comment> comments = new ArrayList<>();

  @Column(name = "is_approved")
  private Boolean isApproved;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question")
  private Set<Bookmark> bookmarks = new LinkedHashSet<>();

  public Question(String title, MainCategory mainCategory, SubCategory subCategory) {
    this.title = title;
    this.mainCategory = mainCategory;
    this.subCategory = subCategory;
    this.viewCount = 0L;
    this.isApproved = true;
    this.createdAt = LocalDateTime.now();
  }

  public void addViewCount() {
    this.viewCount += 1;
  }

  public void addTailQuestions(List<TailQuestion> tailQuestions) {
    this.tailQuestions = new LinkedHashSet<>(tailQuestions);
  }

  public void changeTitle(String title) {
    this.title = title;
  }

  public void changeTailQuestions(List<TailQuestion> tailQuestions) {
    this.tailQuestions = new LinkedHashSet<>(tailQuestions);
  }
}