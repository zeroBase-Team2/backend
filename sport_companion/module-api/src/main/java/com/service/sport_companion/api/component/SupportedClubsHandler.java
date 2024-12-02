package com.service.sport_companion.api.component;

import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.SupportedClubsEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.type.FailedResultType;
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

  public void validateSupportClub(UsersEntity user) {
    if (supportedClubsRepository.existsByUser(user)) {
      throw new GlobalException(FailedResultType.PROVIDER_ID_NOT_FOUND);
    }
  }

  public SupportedClubsEntity findSupportClubsByUserIdAndClubName(Long userId, String clubName) {
    return supportedClubsRepository.findByUserUserIdAndClubClubName(userId, clubName)
        .orElseThrow(() -> new GlobalException(FailedResultType.PROVIDER_ID_NOT_FOUND));
  }

  public void deleteSupportClubs(SupportedClubsEntity supportClubs) {
    supportedClubsRepository.delete(supportClubs);
  }
}
