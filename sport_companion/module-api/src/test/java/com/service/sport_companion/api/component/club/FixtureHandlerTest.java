package com.service.sport_companion.api.component.club;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.FixturesEntity;
import com.service.sport_companion.domain.model.dto.response.fixtures.Fixtures;
import com.service.sport_companion.domain.repository.FixturesRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FixtureHandlerTest {

  @Mock
  private FixturesRepository fixturesRepository;

  @InjectMocks
  private FixtureHandler fixtureHandler;

  private final LocalDate FIXTURE_DATE = LocalDate.now();


  private List<FixturesEntity> fixturesList;
  private List<Fixtures> fixtures;

  @BeforeEach
  void setUp() {
    ClubsEntity homeClub1 = ClubsEntity.builder().clubId(4L).clubName("Mock Home Club 1").build();
    ClubsEntity awayClub1 = ClubsEntity.builder().clubId(10L).clubName("Mock Away Club 1").build();

    ClubsEntity homeClub2 = ClubsEntity.builder().clubId(4L).clubName("Mock Home Club 2").build();
    ClubsEntity awayClub2 = ClubsEntity.builder().clubId(10L).clubName("Mock Away Club 2").build();

    fixturesList = List.of(
        FixturesEntity.builder()
            .fixtureId(4233L)
            .fixtureDate(LocalDate.parse("2019-10-25"))
            .fixtureTime(LocalTime.parse("18:30"))
            .homeScore(5)
            .awayScore(0)
            .notes("-")
            .homeClub(homeClub1)
            .awayClub(awayClub1)
            .season("정규 시즌")
            .build(),
        FixturesEntity.builder()
            .fixtureId(4234L)
            .fixtureDate(LocalDate.parse("2019-10-26"))
            .fixtureTime(LocalTime.parse("14:00"))
            .homeScore(11)
            .awayScore(0)
            .notes("-")
            .homeClub(homeClub2)
            .awayClub(awayClub2)
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
  @DisplayName("크롤링 정보 저장 성공")
  void saveFixtureListSuccessfully() {
    // given
    when(fixturesRepository.saveAll(fixturesList)).thenReturn(fixturesList);

    // when
    List<FixturesEntity> response = fixturesRepository.saveAll(fixturesList);

    // then
    assertEquals(response, fixturesList);
  }

  @Test
  @DisplayName("모든 구단 경기 정보 조회 성공")
  void shouldReturnFixtureListByDateAndSeason() {
    // given
    when(fixturesRepository.findAllByFixtureDate(FIXTURE_DATE)).thenReturn(fixturesList);

    // when
    List<Fixtures> response = fixtureHandler.getAllFixturesList(FIXTURE_DATE);

    // then
    assertEquals(response.size(), fixtures.size());
    assertEquals(response.getFirst().getHomeClubName(), fixtures.getFirst().getHomeClubName());
    assertEquals(response.getFirst().getAwayClubName(), fixtures.getFirst().getAwayClubName());
  }

  @Test
  @DisplayName("선호 구단 경기 정보 조회 성공")
  void shouldReturnSupportClubFixtures() {
    // given
    ClubsEntity homeClub1 = ClubsEntity.builder().clubId(4L).clubName("Mock Home Club 1").build();
    ClubsEntity awayClub1 = ClubsEntity.builder().clubId(10L).clubName("Mock Away Club 1").build();
    when(fixturesRepository.findSupportFixtures(FIXTURE_DATE, homeClub1)).thenReturn(fixturesList);

    // when
    List<Fixtures> response = fixtureHandler.getSupportClubFixturesList(FIXTURE_DATE, homeClub1);

    // then
    assertEquals(response.size(), fixtures.size());
    assertEquals(response.getFirst().getHomeClubName(), fixtures.getFirst().getHomeClubName());
    assertEquals(response.getFirst().getAwayClubName(), fixtures.getFirst().getAwayClubName());
  }
}