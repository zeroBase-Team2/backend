package com.service.sport_companion.domain.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessResultType {

  // Auth
  SUCCESS_SIGNUP_REQUIRED(HttpStatus.OK, "회원가입이 필요한 회원입니다."),
  SUCCESS_SIGNUP(HttpStatus.CREATED, "회원가입 성공!"),
  SUCCESS_LOGIN(HttpStatus.OK, "로그인 성공!"),
  AVAILABLE_NICKNAME(HttpStatus.OK, "사용 가능한 닉네임입니다."),
  UNAVAILABLE_NICKNAME(HttpStatus.OK, "이미 사용 중인 닉네임입니다."),
  SUCCESS_REISSUE_TOKEN(HttpStatus.OK, "Access 토큰 재발급 성공."),

  //Club
  SUCCESS_GET_ALL_CLUBS_LIST(HttpStatus.OK, "모든 구단 조회 성공!"),
  SUCCESS_ADD_CLUB(HttpStatus.OK, "구단 등록 성공"),

  // News
  SUCCESS_GET_NEWS_LIST(HttpStatus.OK, "뉴스를 성공적으로 조회했습니다."),

  // Fixtures
  SUCCESS_CRAWL_FIXTURE(HttpStatus.OK, "크롤링 성공"),
  SUCCESS_GET_ALL_FIXTURES(HttpStatus.OK, "모든 경기 일정 조회 성공"),

  // CustomTopic
  SUCCESS_CREATE_CUSTOM_TOPIC(HttpStatus.CREATED, "주제가 작성되었습니다."),
  SUCCESS_DELETE_CUSTOM_TOPIC(HttpStatus.OK, "주제가 삭제되었습니다"),
  SUCCESS_GET_CUSTOM_TOPIC(HttpStatus.OK, "주제를 성공적으로 조회했습니다."),
  SUCCESS_RECOMMEND_TOPIC(HttpStatus.OK, "주제를 추천했습니다."),

  ;

  private final HttpStatus status;
  private final String message;
}
