package com.service.sport_companion.api.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.service.sport_companion.api.component.club.FixtureHandler;
import com.service.sport_companion.api.component.club.SupportedClubsHandler;
import com.service.sport_companion.api.component.crawl.CrawlFixtures;
import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.FixturesEntity;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.fixtures.Fixtures;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FixturesServiceImplTest {

  @Mock
  private CrawlFixtures crawlFixtures;

  @Mock
  private SupportedClubsHandler supportedClubsHandler;

  @Mock
  private FixtureHandler fixtureHandler;

  @InjectMocks
  private FixturesServiceImpl fixturesService;

  private final String YEAR = "2024";
  private final String MONTH = "12";
  private final String DAY = "05";
  private final String SEASON_NAME = "정규 시즌";
  private final Long USERID = 1L;

  private LocalDate localDate = null;
  private List<Fixtures> fixtures;
  private ClubsEntity homeClub;
  private ClubsEntity awayClub;


  @BeforeEach
  void setUp() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = YEAR + "-" + MONTH + "-" + DAY;
    localDate = LocalDate.parse(formattedDate, formatter);


    homeClub = ClubsEntity.builder().clubId(4L).clubName("Mock Home Club 1").build();
    awayClub = ClubsEntity.builder().clubId(10L).clubName("Mock Away Club 1").build();

    List<FixturesEntity> fixturesList = List.of(
        FixturesEntity.builder()
            .fixtureId(4233L)
            .fixtureDate(LocalDate.parse("2024-12-05"))
            .fixtureTime(LocalTime.parse("18:30"))
            .homeScore(5)
            .awayScore(0)
            .notes("-")
            .homeClub(homeClub)
            .awayClub(awayClub)
            .season("정규 시즌")
            .build()
    );

    fixtures = fixturesList.stream()
        .map(fixture -> new Fixtures(
            fixture.getSeason(),
            fixture.getFixtureDate(),
            fixture.getFixtureTime(),
            fixture.getHomeClub().getClubName(),
            fixture.getHomeScore(),
            fixture.getAwayClub().getClubName(),
            fixture.getAwayScore(),
            fixture.getHomeClub().getClubStadium(),
            fixture.getNotes()
        ))
        .toList();
  }

  @Test
  @DisplayName("년도 별 경기 일정 크롤링 성공")
  void crawlFixturesSuccessfully() {
    // given
    doNothing().when(crawlFixtures).crawlFixtures(YEAR);

    // when
    ResultResponse<Void> resultResponse = fixturesService.crawlFixtures(YEAR);

    // then
    assertEquals(SuccessResultType.SUCCESS_CRAWL_FIXTURE.getStatus(), resultResponse.getStatus());
  }

  @Test
  @DisplayName("선호구단 경기 일정 조회 성공")
  void getSupportClubFixturesSuccessfully() {
    // given
    when(supportedClubsHandler.findSupportClubsByUserId(USERID)).thenReturn(homeClub);
    when(fixtureHandler.getSupportClubFixturesList(localDate, homeClub)).thenReturn(fixtures);

    // when
    ResultResponse<List<Fixtures>> resultResponse = fixturesService.getFixtureList(USERID, YEAR, MONTH, DAY);

    // then
    assertEquals(SuccessResultType.SUCCESS_GET_ALL_FIXTURES.getStatus(), resultResponse.getStatus());
    assertEquals(fixtures.size(), resultResponse.getData().size());
    assertEquals(fixtures.getFirst().getHomeClubName(), resultResponse.getData().getFirst().getHomeClubName());
  }

  @Test
  @DisplayName("선호구단 경기 일정 조회 실패")
  void getSupportClubFixturesFailed() {
    // given
    when(supportedClubsHandler.findSupportClubsByUserId(USERID))
        .thenThrow(new GlobalException(FailedResultType.SUPPORT_NOT_FOUND));

    // when & then
    assertThrows(GlobalException.class,
        () -> fixturesService.getFixtureList(USERID, YEAR, MONTH, DAY));

  }

  @Test
  @DisplayName("모든 구단 경기 일정 조회 성공")
  void getAllClubFixturesSuccessfully() {
    // given
    when(fixtureHandler.getAllFixturesList(localDate)).thenReturn(fixtures);

    // when
    ResultResponse<List<Fixtures>> resultResponse = fixturesService.getFixtureList(USERID, YEAR, MONTH, DAY);

    // then
    assertEquals(SuccessResultType.SUCCESS_GET_ALL_FIXTURES.getStatus(), resultResponse.getStatus());
    assertEquals(fixtures.size(), resultResponse.getData().size());
    assertEquals(fixtures.getFirst().getHomeClubName(), resultResponse.getData().getFirst().getHomeClubName());
  }

}