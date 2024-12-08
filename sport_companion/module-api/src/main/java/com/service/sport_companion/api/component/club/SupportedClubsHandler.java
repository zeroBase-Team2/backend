package com.service.sport_companion.api.component.club;

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

  /**
   * 선호 구단 저장
   */
  public void saveSupportedClub(SupportedClubsEntity supportedClub) {
    supportedClubsRepository.save(supportedClub);
  }

  /**
   * 사용자 ID와 일치하는 선호 구단 정보 조회
   */
  public ClubsEntity findSupportClubsByUserId(Long userId) {
    return supportedClubsRepository.findByUserUserId(userId)
        .map(SupportedClubsEntity::getClub)
        .orElseThrow(() -> new GlobalException(FailedResultType.SUPPORT_NOT_FOUND));
  }

  /**
   * 사용자 선호 구단 등록 여부 조회
   */
  public void validateSupportClub(UsersEntity user) {
    if (supportedClubsRepository.existsByUser(user)) {
      throw new GlobalException(FailedResultType.SUPPORT_CLUB_ALREADY_REGISTERED);
    }
  }

  /**
   * 사용자와 입력한 선호 구단과 일치하는 구단 정보 조회
   */
  public SupportedClubsEntity findSupportClubsByUserIdAndClubName(Long userId, String clubName) {
    return supportedClubsRepository.findByUserUserIdAndClubClubName(userId, clubName)
        .orElseThrow(() -> new GlobalException(FailedResultType.SUPPORT_NOT_FOUND));
  }

  /**
   * 선호 구단 삭제
   */
  public void deleteSupportClubs(SupportedClubsEntity supportClubs) {
    supportedClubsRepository.delete(supportClubs);
  }
}
