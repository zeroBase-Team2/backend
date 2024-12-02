package com.service.sport_companion.api.service.impl;

import com.service.sport_companion.api.component.ClubsHandler;
import com.service.sport_companion.api.component.SupportedClubsHandler;
import com.service.sport_companion.api.component.UserHandler;
import com.service.sport_companion.api.service.SupportClubService;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.SupportedClubsEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.support.SupportClub;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportClubServiceImpl implements SupportClubService {

  private final SupportedClubsHandler supportedClubsHandler;
  private final UserHandler userHandler;
  private final ClubsHandler clubsHandler;

  @Override
  public ResultResponse<SupportClub> getSupportClub(Long userId) {
    ClubsEntity club = supportedClubsHandler.findSupportClubsByUserId(userId);

    SupportClub supportClub = new SupportClub(club.getClubName(), club.getEmblemImg());
    return new ResultResponse<>(SuccessResultType.SUCCESS_GET_SUPPORT_CLUB, supportClub);
  }

  @Override
  public ResultResponse<Void> addSupportClub(Long userId, String clubName) {
    UsersEntity user = userHandler.findByUserId(userId);
    ClubsEntity clubs = clubsHandler.findByClubName(clubName);

    supportedClubsHandler.validateSupportClub(user);

    supportedClubsHandler.saveSupportedClub(SupportedClubsEntity.builder()
        .user(user)
        .club(clubs)
        .build());

    return ResultResponse.of(SuccessResultType.SUCCESS_ADD_SUPPORT_CLUB);
  }

  @Override
  public ResultResponse<Void> deleteSupportClub(Long userId, String clubName) {
    SupportedClubsEntity supportClubs = supportedClubsHandler
        .findSupportClubsByUserIdAndClubName(userId, clubName);

    supportedClubsHandler.deleteSupportClubs(supportClubs);

    return ResultResponse.of(SuccessResultType.SUCCESS_DELETE_SUPPORT_CLUB);
  }
}
