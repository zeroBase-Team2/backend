package com.service.sport_companion.domain.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessResultType {

  // Auth
  SUCCESS_SIGNUP(HttpStatus.CREATED, "회원가입 성공!"),
  SUCCESS_LOGIN(HttpStatus.OK, "로그인 성공!"),

  ;

  private final HttpStatus status;
  private final String message;
}
