package com.developer.wiki.question.infra;


import com.developer.wiki.question.command.domain.MainCategory;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionSearchRepository;
import com.developer.wiki.question.command.domain.SubCategory;
import com.developer.wiki.question.query.application.SummaryQuestionResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

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

    List<SummaryQuestionResponse> questions = jpaQueryFactory.select(
            Projections.fields(SummaryQuestionResponse.class, question.id, question.title,
                question.mainCategory, question.subCategory, question.viewCount, question.commentCount,
                question.createdAt)).from(question)
        .where(mainCategoryEq(mainCategory), subCategoryEq(mainCategory, subCategory),
            question.isApproved.isTrue()).orderBy(question.id.asc()).offset(pageable.getOffset())
        .limit(pageable.getPageSize() + 1).fetch();

    Long count = jpaQueryFactory.select(question.count()).from(question).fetchOne();

    return new PageImpl<>(questions, pageable, count);
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
}
