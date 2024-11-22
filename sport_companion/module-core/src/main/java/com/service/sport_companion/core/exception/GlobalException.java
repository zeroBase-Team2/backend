package com.service.sport_companion.core.exception;


import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.type.FailedResultType;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

  private final ResultResponse resultResponse;

  public GlobalException(FailedResultType failedResultCode) {
    super("");
    this.resultResponse = ResultResponse.of(failedResultCode);
  }

}
