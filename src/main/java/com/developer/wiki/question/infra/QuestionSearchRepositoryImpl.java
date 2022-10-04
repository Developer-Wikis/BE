package com.developer.wiki.question.infra;

import static com.developer.wiki.question.command.domain.QQuestion.question;

import com.developer.wiki.question.command.domain.Category;
import com.developer.wiki.question.command.domain.Question;
import com.developer.wiki.question.command.domain.QuestionSearchRepository;
import com.querydsl.core.BooleanBuilder;
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
  public Slice<Question> findSliceBy(Pageable pageable, List<String> categoryList) {

    List<Question> courses = jpaQueryFactory.select(question).from(question)
        .where(categoryEq(categoryList),question.isApproved.isTrue()).orderBy(question.id.asc()).offset(pageable.getOffset())
        .limit(pageable.getPageSize() + 1).fetch();

    boolean hasNext = false;

    if (courses.size() > pageable.getPageSize()) {
      courses.remove(pageable.getPageSize());
      hasNext = true;
    }

    return new SliceImpl<>(courses, pageable, hasNext);
  }

  private BooleanBuilder categoryEq(List<String> categoryList) {
    if (ObjectUtils.isEmpty(categoryList)) {
      return null;
    }
    List<Category> categories = categoryList.stream()
            .map(Category::of)
            .collect(Collectors.toList());

    BooleanBuilder builder = new BooleanBuilder();
    if (categories.get(0).equals(Category.FE_ALL) || categories.get(0).equals(Category.BE_ALL)) {
      getList(categories.get(0)).stream().forEach(c -> builder.or(question.category.eq(c)));
      return builder;
    }
    categories.stream().forEach(c->builder.or(question.category.eq(c)));
    return builder;
  }

  private List<Category> getList(Category category) {
    return category.equals(Category.FE_ALL) ? Category.frontendAll() : Category.backendAll();
  }
}
