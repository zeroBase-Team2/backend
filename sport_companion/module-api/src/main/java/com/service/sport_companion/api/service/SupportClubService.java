package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.support.SupportClub;

public interface SupportClubService {

  // 선호 구단 조회
  ResultResponse<SupportClub> getSupportClub(Long userId);

  // 선호 구단 등록
  ResultResponse<Void> addSupportClub(Long userId, String clubName);

  // 선호 구단 삭제
  ResultResponse<Void> deleteSupportClub(Long userId, String clubName);
}
