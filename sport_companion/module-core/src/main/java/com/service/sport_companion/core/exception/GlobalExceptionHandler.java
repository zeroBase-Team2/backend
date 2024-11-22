package com.service.sport_companion.core.exception;

import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<ResultResponse> handleGlobalException(GlobalException ex) {
    ResultResponse resultResponse = ex.getResultResponse();
    return new ResponseEntity<>(resultResponse, resultResponse.getStatus());
  }
}
