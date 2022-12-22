package com.developer.wiki.question.query.application;

import com.developer.wiki.question.command.domain.MainCategory;
import com.developer.wiki.question.command.domain.SubCategory;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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
  private Boolean isBookmarked;

  public SummaryQuestionResponse(Long id, String title, MainCategory mainCategory,
      SubCategory subCategory, Long viewCount, Long commentCount, LocalDateTime localDateTime,
      Boolean isBookmarked) {
    this.id = id;
    this.title = title;
    this.mainCategory = mainCategory.name();
    this.subCategory = subCategory.getCategory();
    this.viewCount = viewCount;
    this.commentCount = commentCount;
    this.createdAt = localDateTime;
    this.isBookmarked = isBookmarked;
  }
}
