package com.service.sport_companion.domain.model.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpDto {

  private String providerId;
  private String nickname;
  private String clubName;

}
