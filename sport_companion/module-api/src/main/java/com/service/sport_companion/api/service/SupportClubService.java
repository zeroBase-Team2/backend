package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.support.SupportClub;

public interface SupportClubService {

  ResultResponse<SupportClub> getSupportClub(Long userId);

  ResultResponse<Void> addSupportClub(Long userId, String clubName);

  ResultResponse<Void> deleteSupportClub(Long userId, String clubName);
}
