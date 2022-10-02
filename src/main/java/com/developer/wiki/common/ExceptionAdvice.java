package com.developer.wiki.common;

import com.developer.wiki.common.exception.BadRequestException;
import com.developer.wiki.common.exception.ConflictException;
import com.developer.wiki.common.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ErrorResponse handleNotFoundException(NotFoundException e) {
    return new ErrorResponse(List.of(e.getMessage()));
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public ErrorResponse badRequestException(BadRequestException e) {
    return new ErrorResponse(List.of(e.getMessage()));
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(ConflictException.class)
  public ErrorResponse handleConflictException(ConflictException e) {
    return new ErrorResponse(List.of(e.getMessage()));
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {BindException.class})
  public ErrorResponse methodArgumentNotValidException(BindException e) {
    List<String> messages = e.getBindingResult().getFieldErrors().stream()
        .map(x -> x.getDefaultMessage()).collect(Collectors.toList());
    return new ErrorResponse(messages);
  }


}
