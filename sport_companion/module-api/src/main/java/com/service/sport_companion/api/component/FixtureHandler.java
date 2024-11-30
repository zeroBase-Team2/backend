package com.service.sport_companion.api.component;

import com.service.sport_companion.domain.entity.FixturesEntity;
import com.service.sport_companion.domain.repository.FixturesRepository;
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
}
