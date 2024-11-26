package com.service.sport_companion.domain.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FailedResultType {

  // Auth
  EMAIL_ALREADY_USED(HttpStatus.BAD_REQUEST, "가입 이력이 있는 이메일 입니다."),
  USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않은 회원 입니다."),

  // API
  UNIQUE_NICKNAME_FAILED(HttpStatus.TOO_MANY_REQUESTS, "닉네임 생성에 실패했습니다."),
  UNIQUE_NICKNAME_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "닉네임 생성 중 문제가 발생했습니다."),
  ACCESS_TOKEN_RETRIEVAL(HttpStatus.UNAUTHORIZED, "액세스 토큰을 가져오는 데 실패했습니다."),
  USER_INFO_RETRIEVAL(HttpStatus.UNAUTHORIZED, "사용자 정보를 가져오는 데 실패했습니다."),

  // Crawling
  MBC_NEWS_PARSING_FAILED(HttpStatus.BAD_GATEWAY, "MBC 뉴스 파싱에 실패했습니다.")
  ;

  private final HttpStatus status;
  private final String message;
}
