package com.banzo.donation.registry.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(RecordNotFoundException.class)
  public ResponseEntity<Object> handleException(RecordNotFoundException exception) {

    log.info("RecordNotFoundException occurred: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleException(MethodArgumentNotValidException exception) {

    log.info("MethodArgumentNotValidException occurred: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleException(ConstraintViolationException exception) {

    log.info("ConstraintViolationException occurred: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<Object> handleException(BindException exception) {

    log.info("BindException occurred: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
  }

  @ExceptionHandler(FileTransferException.class)
  ResponseEntity<Object> handleException(FileTransferException exception) {

    log.error("FileTransferException occurred: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception exception) {

    log.error("Unknown error occurred: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
}
