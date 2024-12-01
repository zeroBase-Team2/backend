package com.service.sport_companion.api.component;

import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.FixturesEntity;
import com.service.sport_companion.domain.entity.SeasonsEntity;
import com.service.sport_companion.domain.model.dto.response.fixtures.Fixtures;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.repository.FixturesRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FixtureHandler {

  private final FixturesRepository fixturesRepository;


  public void saveFixtureList(List<FixturesEntity> fixturesList) {
    fixturesRepository.saveAll(fixturesList);
  }

  public List<Fixtures> getAllFixturesList(LocalDate fixtureDate, SeasonsEntity seasons) {
    return mapToFixturesList(fixturesRepository.findAllByFixtureDateAndSeasons(fixtureDate, seasons));
  }

  public List<Fixtures> getSupportClubFixturesList(LocalDate fixtureDate, SeasonsEntity seasons, ClubsEntity clubs) {
    return mapToFixturesList(fixturesRepository.findSupportFixtures(fixtureDate, seasons, clubs));
  }

  private List<Fixtures> mapToFixturesList(List<FixturesEntity> fixturesEntities) {
    return fixturesEntities.stream()
        .map(fixture -> new Fixtures(
            fixture.getFixtureDate(),
            fixture.getFixtureTime(),
            fixture.getHomeClub().getClubName(),
            fixture.getHomeScore(),
            fixture.getAwayClub().getClubName(),
            fixture.getAwayScore(),
            fixture.getStadium(),
            fixture.getNotes()
        ))
        .toList();
  }
}
