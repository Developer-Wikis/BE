package com.developer.wiki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MariadbTestContainer {

  Logger logger = LoggerFactory.getLogger(getClass());

  @Container
  private static final MariaDBContainer<?> MARIA_DB_CONTAINER = new MariaDBContainer(
      "mariadb:10.5");

  @DynamicPropertySource
  public static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url",MARIA_DB_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.driver-class-name",MARIA_DB_CONTAINER::getDriverClassName);
    registry.add("spring.datasource.username",MARIA_DB_CONTAINER::getUsername);
    registry.add("spring.datasource.password",MARIA_DB_CONTAINER::getPassword);
  }
}