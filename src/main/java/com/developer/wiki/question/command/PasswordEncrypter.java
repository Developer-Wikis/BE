package com.developer.wiki.question.command;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncrypter {

  public static String encrypt(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public static boolean isMatch(String password, String hashedPassword) {
    return BCrypt.checkpw(password, hashedPassword);
  }

}
