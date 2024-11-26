package com.service.sport_companion.api.component;

import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.SignUpDataEntity;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.repository.SignUpDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisHandler {

  private final SignUpDataRepository signUpDataRepository;

  public SignUpDataEntity getSignUpDataByProviderId(String providerId) {
    return signUpDataRepository.findById(providerId)
        .orElseThrow(() -> new GlobalException(FailedResultType.PROVIDER_ID_NOT_FOUND));
  }

}
