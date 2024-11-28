package com.service.sport_companion.domain.model.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoCode {

  @JsonProperty("code")
  private String code;

  @JsonCreator
  public KakaoCode(String code) {
    this.code = code;
  }
}
