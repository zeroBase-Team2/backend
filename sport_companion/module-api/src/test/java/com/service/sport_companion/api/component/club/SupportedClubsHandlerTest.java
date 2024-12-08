package com.service.sport_companion.api.component.club;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.ReservationSiteEntity;
import com.service.sport_companion.domain.entity.SportsEntity;
import com.service.sport_companion.domain.entity.SupportedClubsEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.type.UserRole;
import com.service.sport_companion.domain.repository.SupportedClubsRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SupportedClubsHandlerTest {

  @Mock
  private SupportedClubsRepository supportedClubsRepository;

  @InjectMocks
  private SupportedClubsHandler supportedClubsHandler;

  private static final String EMAIL = "test@email.com";
  private static final String NICKNAME = "nickname";
  private static final String KAKAO_PROVIDER = "kakao";
  private static final String KAKAO_PROVIDER_ID = "kakao_provider_id";
  private static final Long USER_ID = 1L;
  private static final String CLUB_NAME = "KIA 타이거즈";

  private UsersEntity user;
  private ClubsEntity club;
  private SupportedClubsEntity supportedClub;

  @BeforeEach
  void setUp() {
    user = UsersEntity.builder()
        .userId(USER_ID)
        .email(EMAIL)
        .nickname(NICKNAME)
        .provider(KAKAO_PROVIDER)
        .providerId(KAKAO_PROVIDER_ID)
        .role(UserRole.USER)
        .createdAt(LocalDateTime.now())
        .build();

    club = ClubsEntity.builder()
        .clubId(1L)
        .clubName(CLUB_NAME)
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

  @Test
  @DisplayName("findSupportClubsByUserId : 선호 구단 조회 성공")
  void findSupportClubsByUserIdSuccessfully() {

    // given
    when(supportedClubsRepository.findByUserUserId(USER_ID)).thenReturn(
        Optional.ofNullable(supportedClub));

    // when
    ClubsEntity response = supportedClubsHandler.findSupportClubsByUserId(USER_ID);

    // then
    assertEquals(response, club);
  }

  @Test
  @DisplayName("findSupportClubsByUserId : 선호 구단 조회 실패")
  void findSupportClubsByUserIdFailed() {

    // given
    when(supportedClubsRepository.findByUserUserId(USER_ID))
        .thenReturn(Optional.empty());

    // when & then
    assertThrows(GlobalException.class, () -> supportedClubsHandler.findSupportClubsByUserId(USER_ID));
  }

  @Test
  @DisplayName("validateSupportClub : 이미 등록한 구단일 경우")
  void validateSupportClubFailed() {

    // given
    when(supportedClubsRepository.existsByUser(user)).thenReturn(true);

    // when & then
    assertThrows(GlobalException.class, () -> supportedClubsHandler.validateSupportClub(user));

  }

  @Test
  @DisplayName("validateSupportClub : 등록하지 않은 경우")
  void validateSupportClubSuccess() {

    // given
    when(supportedClubsRepository.existsByUser(user)).thenReturn(false);

    // when & then
    assertDoesNotThrow(() -> supportedClubsHandler.validateSupportClub(user));
  }

  @Test
  @DisplayName("findSupportClubsByUserIdAndClubName : 선호 구단 조회 성공")
  void findSupportClubsByUserIdAndClubNameSuccess() {
    // given
    when(supportedClubsRepository.findByUserUserIdAndClubClubName(USER_ID, CLUB_NAME))
        .thenReturn(Optional.ofNullable(supportedClub));

    // when
    SupportedClubsEntity response = supportedClubsHandler.findSupportClubsByUserIdAndClubName(
        USER_ID, CLUB_NAME);

    // then
    assertEquals(response, supportedClub);
  }

  @Test
  @DisplayName("findSupportClubsByUserIdAndClubName : 선호 구단 조회 실패")
  void findSupportClubsByUserIdAndClubNameFailed() {
    // given
    when(supportedClubsRepository.findByUserUserIdAndClubClubName(USER_ID, CLUB_NAME))
        .thenReturn(Optional.empty());

    // when & then
    assertThrows(GlobalException.class, () -> supportedClubsHandler
        .findSupportClubsByUserIdAndClubName(USER_ID, CLUB_NAME));
  }

  @Test
  @DisplayName("deleteSupportClubs : 선호구단 삭제 성공")
  void deleteSupportClubsSuccess() {
    // given & when
    supportedClubsHandler.deleteSupportClubs(supportedClub);

    // then
    verify(supportedClubsRepository, times(1)).delete(supportedClub);
  }
}