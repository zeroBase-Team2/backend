package com.service.sport_companion.api.component.club;

import com.service.sport_companion.domain.entity.SeasonsEntity;
import com.service.sport_companion.domain.repository.SeasonsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeasonHandler {

  private final SeasonsRepository seasonsRepository;

  public SeasonsEntity findBySeasonName(String seasonName) {
    return seasonsRepository.findBySeasonName(seasonName);
  }

}
