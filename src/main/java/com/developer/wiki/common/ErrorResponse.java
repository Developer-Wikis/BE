package com.developer.wiki.common;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

  private List<String> messages;

  public ErrorResponse(List<String> messages) {
    this.messages = messages;
  }
}
