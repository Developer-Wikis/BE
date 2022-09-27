package com.developer.wiki.common.exception;

public class UnAuthorizedException extends RuntimeException {

  public UnAuthorizedException(String message) {
    super(message);
  }
}
