package com.developer.wiki;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MariadbTestContainer {

  Logger logger = LoggerFactory.getLogger(getClass());

  @Container
  JdbcDatabaseContainer mariaDB = new MariaDBContainer("mariadb:10.5")
      .withUsername("myuser") // DB 사용자
      .withPassword("mypassword") // 암호
      .withDatabaseName("mydb");

  @Test
  void connect() {
    logger.info("host: {}", mariaDB.getHost());
    logger.info("port: {}", mariaDB.getMappedPort(3306));
    logger.info("username: {}", mariaDB.getUsername());
    logger.info("password: {}", mariaDB.getPassword());
    logger.info("jdbc url: {}", mariaDB.getJdbcUrl());
  }
}