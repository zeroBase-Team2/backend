package com.service.sport_companion.api.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.SignUpDataEntity;
import com.service.sport_companion.domain.repository.SignUpDataRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RedisHandlerTest {

  @Mock
  private SignUpDataRepository signUpDataRepository;

  @InjectMocks
  private RedisHandler redisHandler;

  private final String PROVIDER_ID = "providerId";
  private final String PROVIDER = "kakao";
  private final String EMAIL = "example@example.com";

  private SignUpDataEntity signUpData;

  @BeforeEach
  void setUp() {
    signUpData = SignUpDataEntity.builder()
        .providerId(PROVIDER_ID)
        .provider(PROVIDER)
        .email(EMAIL)
        .build();
  }

  @Test
  @DisplayName("getSignUpDataByProviderId : 캐시 데이터 조회 성공")
  void shouldReturnSignUpData() {
    // given
    when(signUpDataRepository.findById(PROVIDER_ID)).thenReturn(Optional.of(signUpData));

    // when
    SignUpDataEntity response = redisHandler.getSignUpDataByProviderId(PROVIDER_ID);

    // then
    assertEquals(response.getProviderId(), signUpData.getProviderId());
    assertEquals(response.getProvider(), signUpData.getProvider());
    assertEquals(response.getEmail(), signUpData.getEmail());
  }

  @Test
  @DisplayName("getSignUpDataByProviderId : 캐시 데이터 조회 실패")
  void shouldReturnExceptionProviderNotFound() {
    // given
    when(signUpDataRepository.findById(PROVIDER_ID)).thenReturn(Optional.empty());

    // when & then
    assertThrows(GlobalException.class, () -> redisHandler.getSignUpDataByProviderId(PROVIDER_ID));
  }
}