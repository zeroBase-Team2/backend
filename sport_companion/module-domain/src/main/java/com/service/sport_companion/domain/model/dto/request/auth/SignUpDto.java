package com.service.sport_companion.domain.model.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SignUpDto {

  @JsonProperty("providerId")
  private String providerId;

  @JsonProperty("nickname")
  private String nickname;

  @JsonProperty("clubName")
  private String clubName;

  @JsonCreator
  public SignUpDto(String providerId, String nickname, String clubName) {
    this.providerId = providerId;
    this.nickname = nickname;
    this.clubName = clubName;
  }
}
