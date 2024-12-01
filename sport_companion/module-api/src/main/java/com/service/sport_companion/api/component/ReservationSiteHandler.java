package com.service.sport_companion.api.component;

import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.ReservationSiteEntity;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.repository.ReservationSiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationSiteHandler {

  private final ReservationSiteRepository reservationSiteRepository;

  public ReservationSiteEntity findBySiteName(String siteName) {
    return reservationSiteRepository.findBySiteName(siteName)
        .orElseThrow(() -> new GlobalException(FailedResultType.PROVIDER_ID_NOT_FOUND));
  }

}
