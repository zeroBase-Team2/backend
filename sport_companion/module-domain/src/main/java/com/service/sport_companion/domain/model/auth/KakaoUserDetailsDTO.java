package com.service.sport_companion.domain.model.auth;

import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KakaoUserDetailsDTO {

  private Map<String, Object> attributes;

  public String getProviderId() {
    return attributes.get("id").toString();
  }

  public String getProvider() {
    return "kakao";
  }

  public String getEmail() {
    return (String) ((Map<?, ?>) attributes.get("kakao_account")).get("email");
  }
}
