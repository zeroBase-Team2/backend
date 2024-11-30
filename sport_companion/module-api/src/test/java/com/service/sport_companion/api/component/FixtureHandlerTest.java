package com.service.sport_companion.api.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.FixturesEntity;
import com.service.sport_companion.domain.entity.SeasonsEntity;
import com.service.sport_companion.domain.repository.FixturesRepository;
import java.time.LocalDate;
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

  private List<FixturesEntity> fixturesList;

  @BeforeEach
  void setUp() {
    ClubsEntity homeClub1 = ClubsEntity.builder().clubId(4L).clubName("Mock Home Club 1").build();
    ClubsEntity awayClub1 = ClubsEntity.builder().clubId(10L).clubName("Mock Away Club 1").build();

    ClubsEntity homeClub2 = ClubsEntity.builder().clubId(4L).clubName("Mock Home Club 2").build();
    ClubsEntity awayClub2 = ClubsEntity.builder().clubId(10L).clubName("Mock Away Club 2").build();

    SeasonsEntity season = SeasonsEntity.builder().seasonId(3L).seasonName("Mock Season").build();

    fixturesList = List.of(
        FixturesEntity.builder()
            .fixtureId(4233L)
            .fixtureDate(LocalDate.parse("2019-10-25"))
            .fixtureTime("18:30")
            .homeScore("5")
            .awayScore("0")
            .notes("-")
            .stadium("고척")
            .homeClub(homeClub1)
            .awayClub(awayClub1)
            .seasons(season)
            .build(),
        FixturesEntity.builder()
            .fixtureId(4234L)
            .fixtureDate(LocalDate.parse("2019-10-26"))
            .fixtureTime("14:00")
            .homeScore("11")
            .awayScore("9")
            .notes("-")
            .stadium("고척")
            .homeClub(homeClub2)
            .awayClub(awayClub2)
            .seasons(season)
            .build()
    );
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
}