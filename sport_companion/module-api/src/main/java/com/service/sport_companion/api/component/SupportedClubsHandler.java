package com.service.sport_companion.api.component;

import com.service.sport_companion.domain.entity.ClubsEntity;
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

  public ClubsEntity findSupportClubsByUserId(Long userId) {
    return supportedClubsRepository.findByUserUserId(userId)
        .map(SupportedClubsEntity::getClub)
        .orElse(new ClubsEntity());
  }
}
