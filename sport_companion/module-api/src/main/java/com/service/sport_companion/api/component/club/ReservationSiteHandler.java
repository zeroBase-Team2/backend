package com.service.sport_companion.api.component.club;

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

  /**
   * 사이트 명과 일치하는 ReservationSiteEntity 조회
   */
  public ReservationSiteEntity findBySiteName(String siteName) {
    return reservationSiteRepository.findBySiteName(siteName)
        .orElseThrow(() -> new GlobalException(FailedResultType.RESERVATION_SITE_NOT_FOUND));
  }

}
