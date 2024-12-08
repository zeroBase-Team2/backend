package com.service.sport_companion.api.component.club;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.ReservationSiteEntity;
import com.service.sport_companion.domain.repository.ReservationSiteRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationSiteHandlerTest {

  @Mock
  private ReservationSiteRepository reservationSiteRepository;

  @InjectMocks
  private ReservationSiteHandler reservationSiteHandler;

  private final String SITE_NAME = "티켓링크";

  private ReservationSiteEntity reservationSite;

  @BeforeEach
  void setUp() {
    reservationSite = ReservationSiteEntity.builder()
        .reservationSiteId(1L)
        .siteName(SITE_NAME)
        .siteUrl("https://~~~")
        .build();
  }


  @Test
  @DisplayName("예매처 정보 조회 성공")
  void findReservationSiteBySiteNameSuccess() {
    // given
    when(reservationSiteRepository.findBySiteName(SITE_NAME)).thenReturn(
        Optional.of(reservationSite));

    // when
    ReservationSiteEntity response = reservationSiteHandler.findBySiteName(SITE_NAME);

    // then
    assertEquals(reservationSite, response);
  }

  @Test
  @DisplayName("예매처 정보 조회 실패")
  void findReservationSiteBySiteNameFailed() {
    // given
    when(reservationSiteRepository.findBySiteName(SITE_NAME)).thenReturn(Optional.empty());

    // when & then
    assertThrows(GlobalException.class, () -> reservationSiteHandler.findBySiteName(SITE_NAME));
  }

}