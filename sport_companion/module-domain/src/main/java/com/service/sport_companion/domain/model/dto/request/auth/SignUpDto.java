package com.service.sport_companion.domain.model.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.service.sport_companion.domain.entity.SignUpDataEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.type.UserRole;
import java.time.LocalDateTime;
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

  public static UsersEntity of(SignUpDataEntity signUpData, String nickname) {
    return UsersEntity.builder()
        .email(signUpData.getEmail())
        .nickname(nickname)
        .provider(signUpData.getProvider())
        .providerId(signUpData.getProviderId())
        .role(UserRole.USER)
        .createdAt(LocalDateTime.now())
        .build();
  }
}
