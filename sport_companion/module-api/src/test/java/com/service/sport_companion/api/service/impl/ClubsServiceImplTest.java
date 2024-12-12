package com.service.sport_companion.api.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.service.sport_companion.api.component.S3Handler;
import com.service.sport_companion.api.component.UserHandler;
import com.service.sport_companion.api.component.club.ClubsFacade;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.SupportedClubsEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.dto.request.club.ClubDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.clubs.Clubs;
import com.service.sport_companion.domain.model.dto.response.support.SupportClub;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class ClubsServiceImplTest {

  @Mock
  private ClubsFacade clubsFacade;

  @Mock
  private S3Handler s3Handler;

  @Mock
  private UserHandler userHandler;

  @InjectMocks
  private ClubsServiceImpl clubsService;

  private static final String CLUB_NAME = "KIA 타이거즈";
  private static final String STADIUM = "광주 기아 챔피언스 필드";
  private static final String STADIUM_ADDRESS = "광주 기아 챔피언스 필드 주소";
  private static final String SITE_URL = "siteUrl1";
  private static final String EMBLEM_IMG = "imageUrl";
  private static final Long USER_ID = 1L;

  private List<Clubs> clubsList;
  private ClubDto clubDto;
  private UsersEntity user;
  private ClubsEntity clubEntity;
  private SupportedClubsEntity supportedClub;

  @BeforeEach
  void setUp() {
    clubDto = new ClubDto(
        CLUB_NAME,
        STADIUM,
        STADIUM_ADDRESS,
        SITE_URL,
        new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes())
    );

    clubEntity = ClubsEntity.builder()
        .clubId(1L)
        .clubName(CLUB_NAME)
        .clubStadium(STADIUM)
        .stadiumAddress(STADIUM_ADDRESS)
        .reservationSite(SITE_URL)
        .emblemImg(EMBLEM_IMG)
        .build();

    clubsList = List.of(new Clubs(clubEntity.getClubName(), clubEntity.getEmblemImg()));

    user = UsersEntity.builder()
        .userId(USER_ID)
        .nickname("TestUser")
        .build();

    supportedClub = SupportedClubsEntity.builder()
        .user(user)
        .club(clubEntity)
        .build();
  }

  @Test
  @DisplayName("모든 클럽 리스트 가져오기 성공")
  void testGetAllClubList() {
    // given
    when(clubsFacade.getAllClubList()).thenReturn(clubsList);

    // when
    ResultResponse<List<Clubs>> resultResponse = clubsService.getAllClubList();

    // then
    assertEquals(SuccessResultType.SUCCESS_GET_ALL_CLUBS_LIST.getStatus(), resultResponse.getStatus());
  }

  @Test
  @DisplayName("구단 등록 성공")
  void testAddClub() throws IOException {
    // given
    when(s3Handler.upload(clubDto.getFile())).thenReturn(EMBLEM_IMG);

    // when
    ResultResponse<Void> resultResponse = clubsService.addClub(clubDto);

    // then
    assertEquals(SuccessResultType.SUCCESS_ADD_CLUB.getStatus(), resultResponse.getStatus());
  }

  @Test
  @DisplayName("선호 구단 조회 성공")
  void testGetSupportClub() {
    // given
    when(clubsFacade.getSupportClubsByUserId(user.getUserId())).thenReturn(clubEntity);

    // when
    ResultResponse<SupportClub> resultResponse = clubsService.getSupportClub(user.getUserId());

    // then
    assertEquals(SuccessResultType.SUCCESS_GET_SUPPORT_CLUB.getStatus(), resultResponse.getStatus());
  }

  @Test
  @DisplayName("선호 구단 등록 성공")
  void testAddSupportClub() {
    // given
    when(userHandler.findByUserId(user.getUserId())).thenReturn(user);

    // when
    ResultResponse<Void> resultResponse = clubsService.addSupportClub(user.getUserId(), CLUB_NAME);

    // then
    assertEquals(SuccessResultType.SUCCESS_ADD_SUPPORT_CLUB.getStatus(), resultResponse.getStatus());
  }

  @Test
  @DisplayName("선호 구단 삭제 성공")
  void testDeleteSupportClub() {
    // when
    ResultResponse<Void> resultResponse = clubsService.deleteSupportClub(user.getUserId(), CLUB_NAME);

    // then
    assertEquals(SuccessResultType.SUCCESS_DELETE_SUPPORT_CLUB.getStatus(), resultResponse.getStatus());
  }
}