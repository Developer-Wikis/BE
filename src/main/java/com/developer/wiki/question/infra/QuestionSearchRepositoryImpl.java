package com.developer.wiki.question.infra;


import com.developer.wiki.question.command.domain.MainCategory;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionSearchRepository;
import com.developer.wiki.question.command.domain.SubCategory;
import com.developer.wiki.question.query.application.SummaryQuestionResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.developer.wiki.bookmark.QBookmark.bookmark;
import static com.developer.wiki.question.command.domain.QQuestion.question;

@Repository
public class QuestionSearchRepositoryImpl implements QuestionSearchRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public QuestionSearchRepositoryImpl(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<SummaryQuestionResponse> findPageByUserId(Pageable pageable, String mainCategory,
      List<String> subCategory, Long userId) {
    List<Question> questions = jpaQueryFactory.select(question).from(question)
        .leftJoin(question.bookmarks)
        .where(mainCategoryEq(mainCategory), subCategoryEq(mainCategory, subCategory),
            question.isApproved.isTrue()).orderBy(question.id.asc()).offset(pageable.getOffset())
        .limit(pageable.getPageSize() + 1).distinct().fetch();
    List<SummaryQuestionResponse> summaryQuestionResponses = questions.stream().map(question -> {
      Boolean isBookmarked = exist(question.getId(), userId);
      return new SummaryQuestionResponse(question.getId(), question.getTitle(),
          question.getMainCategory(), question.getSubCategory(), question.getViewCount(),
          question.getCommentCount(), question.getCreatedAt(), isBookmarked);
    }).collect(Collectors.toList());
    System.out.println(summaryQuestionResponses);
    Long count = jpaQueryFactory.select(question.count()).from(question).fetchOne();
    return new PageImpl<>(summaryQuestionResponses, pageable, count);
  }

  @Override
  public Page<SummaryQuestionResponse> findBookmarkByUserId(Pageable pageable, String mainCategory,
      List<String> subCategory, Long userId) {
    List<Question> questions = jpaQueryFactory.select(question).from(question)
        .leftJoin(question.bookmarks)
        .where(question.bookmarks.any().userId.eq(userId), mainCategoryEq(mainCategory),
            subCategoryEq(mainCategory, subCategory), question.isApproved.isTrue())
        .orderBy(question.id.asc()).offset(pageable.getOffset()).limit(pageable.getPageSize() + 1)
        .distinct().fetch();
    List<SummaryQuestionResponse> summaryQuestionResponses = questions.stream().map(question -> {
      Boolean isBookmarked = exist(question.getId(), userId);
      return new SummaryQuestionResponse(question.getId(), question.getTitle(),
          question.getMainCategory(), question.getSubCategory(), question.getViewCount(),
          question.getCommentCount(), question.getCreatedAt(), isBookmarked);
    }).collect(Collectors.toList());
    System.out.println(summaryQuestionResponses);
    Long count = jpaQueryFactory.select(question.count()).from(question).fetchOne();
    return new PageImpl<>(summaryQuestionResponses, pageable, count);
  }

  @Override
  public Slice<Question> findRandomBy(Pageable pageable, String mainCategory,
      List<String> subCategory) {
    List<Question> courses = jpaQueryFactory.select(question).from(question)
        .where(mainCategoryEq(mainCategory), subCategoryEq(mainCategory, subCategory),
            question.isApproved.isTrue()).orderBy(question.id.asc()).offset(pageable.getOffset())
        .limit(pageable.getPageSize() + 1).fetch();
    boolean hasNext = false;
    Collections.shuffle(courses);
    if (courses.size() > pageable.getPageSize()) {
      courses.remove(pageable.getPageSize());
      hasNext = true;
    }

    return new SliceImpl<>(courses, pageable, hasNext);
  }

  private BooleanExpression mainCategoryEq(String mainCategory) {
    return ObjectUtils.isEmpty(mainCategory) ? null
        : question.mainCategory.eq(MainCategory.of(mainCategory));
  }

  private BooleanBuilder subCategoryEq(String mainCategory, List<String> subCategories) {
    if (ObjectUtils.isEmpty(subCategories)) {
      return null;
    }
    BooleanBuilder builder = new BooleanBuilder();
    if (subCategories.get(0).equals(SubCategory.all.name())) {
      List<String> subCategoryList = SubCategory.of(subCategories.get(0))
          .allOrOneSubCategory(MainCategory.of(mainCategory));
      subCategoryList.stream().map(SubCategory::ofForQuery).collect(Collectors.toList())
          .forEach(s -> builder.or(question.subCategory.eq(s)));
      return builder;
    }
    subCategories.stream().map(SubCategory::of).collect(Collectors.toList())
        .forEach(s -> builder.or(question.subCategory.eq(s)));
    return builder;
  }

  private BooleanExpression userIdEq(Long userId) {
    return Objects.isNull(userId) ? null : bookmark.userId.eq(userId);
  }

  public Boolean exist(Long questionId, Long userId) {
    if (userId == null) {
      userId = 0L;
    }
    Integer fetchOne = jpaQueryFactory.selectOne().from(bookmark)
        .where(bookmark.question.id.eq(questionId), userIdEq(userId)).fetchFirst();

    return fetchOne != null;

  }

}
