package com.service.sport_companion.domain.model.dto.response.fixtures;

import com.service.sport_companion.domain.entity.FixturesEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Fixtures {

  private Long fixtureId;

  private String season;

  private LocalDate fixtureDate;

  private String fixtureTime;

  private String homeClubName;

  private int homeScore;

  private String awayClubName;

  private int awayScore;

  private String stadium;

  private String stadiumAddress;

  private String notes;

  public static List<Fixtures> of(List<FixturesEntity> fixturesEntities) {
    return fixturesEntities.stream()
        .map(fixture -> new Fixtures(
            fixture.getFixtureId(),
            fixture.getSeason(),
            fixture.getFixtureDate(),
            fixture.getFixtureTime(),
            fixture.getHomeClub().getClubName(),
            fixture.getHomeScore(),
            fixture.getAwayClub().getClubName(),
            fixture.getAwayScore(),
            fixture.getHomeClub().getClubStadium(),
            fixture.getHomeClub().getStadiumAddress(),
            fixture.getNotes()
        )).toList();
  }
}
