package com.service.sport_companion.api.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.service.sport_companion.api.component.ClubsHandler;
import com.service.sport_companion.api.component.SupportedClubsHandler;
import com.service.sport_companion.api.component.UserHandler;
import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.SupportedClubsEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.support.SupportClub;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import com.service.sport_companion.domain.model.type.UserRole;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SupportClubServiceImplTest {

  @Mock
  private SupportedClubsHandler supportedClubsHandler;

  @Mock
  private UserHandler userHandler;

  @Mock
  private ClubsHandler clubsHandler;

  @InjectMocks
  private SupportClubServiceImpl supportClubService;

  private final Long USER_ID = 1L;
  private final String CLUB_NAME = "기아 타이거즈";

  private SupportedClubsEntity supportedClubs;
  private UsersEntity users;
  private ClubsEntity clubs;
  private SupportClub supportClub;

  @BeforeEach
  void setUp() {
    users = UsersEntity.builder()
        .userId(USER_ID)
        .email("test@email.com")
        .nickname("nickname")
        .provider("kakao")
        .providerId("kakao_provider_id")
        .role(UserRole.USER)
        .createdAt(LocalDateTime.now())
        .build();

    clubs = ClubsEntity.builder()
        .clubId(1L)
        .clubName(CLUB_NAME)
        .emblemImg("emblem.png")
        .build();

    supportedClubs = SupportedClubsEntity.builder()
        .user(users)
        .club(clubs)
        .build();

    supportClub = new SupportClub(clubs.getClubName(), clubs.getEmblemImg());
  }

  @Test
  @DisplayName("getSupportClub : 선호 구단 조회 성공")
  void successGetSupportClub() {
    // given
    when(supportedClubsHandler.findSupportClubsByUserId(USER_ID)).thenReturn(clubs);

    // when
    ResultResponse<SupportClub> response = supportClubService.getSupportClub(USER_ID);

    // then
    assertEquals(SuccessResultType.SUCCESS_GET_SUPPORT_CLUB.getStatus(), response.getStatus());
    assertEquals(clubs.getClubName(), response.getData().getClubName());
  }

  @Test
  @DisplayName("getSupportClub : 선호 구단 조회 실패 - 등록하지 않은 선호구단")
  void failGetSupportClub() {
    // given
    when(supportedClubsHandler.findSupportClubsByUserId(USER_ID))
        .thenThrow(new GlobalException(FailedResultType.SUPPORT_NOT_FOUND));

    // when & then
    assertThrows(GlobalException.class, () -> supportClubService.getSupportClub(USER_ID));
  }

  @Test
  @DisplayName("addSupportClub : 선호 구단 등록 성공")
  void successAddSupportClub() {
    // given
    when(userHandler.findByUserId(USER_ID)).thenReturn(users);
    when(clubsHandler.findByClubName(CLUB_NAME)).thenReturn(clubs);
    doNothing().when(supportedClubsHandler).validateSupportClub(users);

    // when
    ResultResponse<Void> response = supportClubService.addSupportClub(USER_ID, CLUB_NAME);

    // then
    assertEquals(SuccessResultType.SUCCESS_ADD_SUPPORT_CLUB.getStatus(), response.getStatus());
  }

  @Test
  @DisplayName("addSupportClub : 선호 구단 등록 실패 - 존재하지 않은 사용자")
  void failedAddSupportClubNotFoundUser() {
    // given
    when(userHandler.findByUserId(USER_ID)).thenThrow(
        new GlobalException(FailedResultType.USER_NOT_FOUND));

    // when & then
    assertThrows(GlobalException.class,
        () -> supportClubService.addSupportClub(USER_ID, CLUB_NAME));
  }

  @Test
  @DisplayName("deleteSupportClub : 선호구단 삭제 성공")
  void successDeleteSupportClub() {
    // given
    when(supportedClubsHandler.findSupportClubsByUserIdAndClubName(USER_ID, CLUB_NAME))
        .thenReturn(supportedClubs);

    // when
    ResultResponse<Void> response = supportClubService.deleteSupportClub(USER_ID, CLUB_NAME);

    // then
    assertEquals(SuccessResultType.SUCCESS_DELETE_SUPPORT_CLUB.getStatus(), response.getStatus());

  }
}