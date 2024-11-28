package com.service.sport_companion.domain.model.dto.response;

import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ResultResponse<T> {

  private final Integer code;
  private final HttpStatus status;
  private final String message;
  private final T data;

  public ResultResponse(SuccessResultType successResultCode, T data) {
    this.code = successResultCode.getStatus().value();
    this.status = successResultCode.getStatus();
    this.message = successResultCode.getMessage();
    this.data = data;
  }

  public ResultResponse(FailedResultType failedResultCode,  T data) {
    this.code = failedResultCode.getStatus().value();
    this.status = failedResultCode.getStatus();
    this.message = failedResultCode.getMessage();
    this.data = data;
  }

  public static ResultResponse<Void> of(SuccessResultType successResultCode) {
    return new ResultResponse<>(successResultCode, null);
  }

  public static ResultResponse<Void> of(FailedResultType failedResultCode) {
    return new ResultResponse<>(failedResultCode, null);
  }

}
