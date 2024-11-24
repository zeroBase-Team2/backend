package com.service.sport_companion.domain.model.type;

import lombok.Getter;

@Getter
public enum UrlType {

  KAKAO_TOKEN_URL("https://kauth.kakao.com/oauth/token"),
  KAKAO_USER_INFO_URL("https://kapi.kakao.com/v2/user/me"),
  RANDOM_NICKNAME_URL("https://www.rivestsoft.com/nickname/getRandomNickname.ajax"),
  SIGNUP_URL("http://localhost:3000/signUp"),
  FRONT_LOCAL_URL("http://localhost:3000"),
  FRONT_VERCEL_URL("https://front-end-jet-psi.vercel.app"),
  ;

  private final String url;

  UrlType(String value) {
    this.url = value;
  }
}
