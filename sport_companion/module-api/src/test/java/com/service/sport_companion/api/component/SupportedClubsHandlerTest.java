package com.service.sport_companion.api.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.ReservationSiteEntity;
import com.service.sport_companion.domain.entity.SportsEntity;
import com.service.sport_companion.domain.entity.SupportedClubsEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.type.UserRole;
import com.service.sport_companion.domain.repository.SupportedClubsRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SupportedClubsHandlerTest {

  @Mock
  private SupportedClubsRepository supportedClubsRepository;

  private static final String EMAIL = "test@email.com";
  private static final String NICKNAME = "nickname";
  private static final String KAKAO_PROVIDER = "kakao";
  private static final String KAKAO_PROVIDER_ID = "kakao_provider_id";

  private UsersEntity user;
  private ClubsEntity club;
  private SupportedClubsEntity supportedClub;

  @BeforeEach
  void setUp() {
    user = UsersEntity.builder()
        .userId(1L)
        .email(EMAIL)
        .nickname(NICKNAME)
        .provider(KAKAO_PROVIDER)
        .providerId(KAKAO_PROVIDER_ID)
        .role(UserRole.USER)
        .createdAt(LocalDateTime.now())
        .build();

    club = ClubsEntity.builder()
        .clubId(1L)
        .clubName("KIA 타이거즈")
        .clubStadium("광주 기아 챔피언스 필드")
        .sports(SportsEntity.builder().sportId(1L).build())
        .emblemImg("imageUrl1")
        .introduction(null)
        .reservationSite(ReservationSiteEntity.builder().reservationSiteId(1L).build())
        .build();

    supportedClub = SupportedClubsEntity.builder()
        .user(user)
        .club(club)
        .build();
  }

  @Test
  @DisplayName("선호 구단 저장 성공")
  void saveSupportedClubSuccessfully() {
    // given
    when(supportedClubsRepository.save(supportedClub)).thenReturn(supportedClub);

    // when
    SupportedClubsEntity response = supportedClubsRepository.save(supportedClub);

    // then
    assertEquals(response, supportedClub);
  }

}