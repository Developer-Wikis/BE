package com.developer.wiki.question.query.application;

import java.time.LocalDateTime;

import com.developer.wiki.question.command.domain.MainCategory;
import com.developer.wiki.question.command.domain.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryQuestionResponse {

  private Long id;
  private String title;
  private String mainCategory;
  private String subCategory;
  private Long viewCount;
  private Long commentCount;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime createdAt;
  public SummaryQuestionResponse(Long id, String title, MainCategory mainCategory, SubCategory subCategory,Long viewCount,Long commentCount, LocalDateTime localDateTime ){
    this.id=id;
    this.title=title;
    this.mainCategory=mainCategory.name();
    this.subCategory=subCategory.getCategory();
    this.viewCount=viewCount;
    this.commentCount=commentCount;
    this.createdAt=localDateTime;
  }
}
