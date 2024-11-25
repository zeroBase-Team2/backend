package com.service.sport_companion.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@RedisHash(value = "SignUpData", timeToLive = 600)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SignUpDataEntity {

  @Id // Redis에서의 Key (주로 providerId 사용)
  private String providerId;
  private String email;
  private String provider;
}
