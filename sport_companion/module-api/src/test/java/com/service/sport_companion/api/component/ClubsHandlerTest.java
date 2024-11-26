package com.service.sport_companion.api.component;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.ReservationSiteEntity;
import com.service.sport_companion.domain.entity.SportsEntity;
import com.service.sport_companion.domain.repository.ClubsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClubsHandlerTest {

  @Mock
  private ClubsRepository clubsRepository;

  @InjectMocks
  private ClubsHandler clubsHandler;

  private final String CLUB_NAME = "KIA 타이거즈";

  private ClubsEntity club;

  @BeforeEach
  void setUp() {
    club = ClubsEntity.builder()
        .clubId(1L)
        .clubName("KIA 타이거즈")
        .clubStadium("광주 기아 챔피언스 필드")
        .sports(SportsEntity.builder().sportId(1L).build())
        .emblemImg("imageUrl1")
        .introduction(null)
        .reservationSite(ReservationSiteEntity.builder().reservationSiteId(1L).build())
        .build();
  }

  @Test
  @DisplayName("findByClubName : 구단 정보 반환 성공")
  void shouldReturnClubs() {
    // given
    when(clubsRepository.findByClubName(CLUB_NAME)).thenReturn(club);

    // when
    ClubsEntity response = clubsHandler.findByClubName(CLUB_NAME);

    // then
    assertEquals(response, club);
  }

}