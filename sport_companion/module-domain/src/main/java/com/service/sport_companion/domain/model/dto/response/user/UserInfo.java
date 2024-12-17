package com.service.sport_companion.domain.model.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfo {

  private String email;
  private String nickname;

  private String clubName;
  private String emblemImg;

}
