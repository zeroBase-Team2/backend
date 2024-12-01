package com.service.sport_companion.api.component;

import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.SportsEntity;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SportHandler {

  private final SportRepository sportRepository;

  public SportsEntity findBySportName(String sportName) {
    return sportRepository.findBySportName(sportName)
        .orElseThrow(() -> new GlobalException(FailedResultType.SPORT_NOT_FOUND));
  }

}
