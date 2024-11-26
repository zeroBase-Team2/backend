package com.service.sport_companion.api.component;

import com.service.sport_companion.domain.entity.SupportedClubsEntity;
import com.service.sport_companion.domain.repository.SupportedClubsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupportedClubsHandler {

  private final SupportedClubsRepository supportedClubsRepository;

  public void saveSupportedClub(SupportedClubsEntity supportedClub) {
    supportedClubsRepository.save(supportedClub);
  }
}
