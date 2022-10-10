package com.developer.wiki.question.infra;

import static com.developer.wiki.question.command.domain.QQuestion.question;

import com.developer.wiki.question.command.domain.MainCategory;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionSearchRepository;
import com.developer.wiki.question.command.domain.SubCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@Repository
public class QuestionSearchRepositoryImpl implements QuestionSearchRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public QuestionSearchRepositoryImpl(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Slice<Question> findSliceBy(Pageable pageable, String mainCategory,
      List<String> subCategories) {

    List<Question> courses = jpaQueryFactory.select(question).from(question)
        .where(mainCategoryEq(mainCategory), subCategoryEq(subCategories),
            question.isApproved.isTrue()).orderBy(question.id.asc()).offset(pageable.getOffset())
        .limit(pageable.getPageSize() + 1).fetch();

    boolean hasNext = false;

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

  private BooleanBuilder subCategoryEq(List<String> subCategories) {
    if (ObjectUtils.isEmpty(subCategories)) {
      return null;
    }
    BooleanBuilder builder = new BooleanBuilder();
    subCategories.stream().map(SubCategory::of).collect(Collectors.toList())
        .forEach(s -> builder.or(question.subCategory.eq(s)));
    return builder;
  }
}
