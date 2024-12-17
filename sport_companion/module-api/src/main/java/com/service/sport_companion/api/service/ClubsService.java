package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.request.club.ClubDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.clubs.Clubs;
import com.service.sport_companion.domain.model.dto.response.support.SupportClub;
import java.io.IOException;
import java.util.List;

public interface ClubsService {

  // 모든 구단 정보 조회
  ResultResponse<List<Clubs>> getAllClubList();

  // 구단 등록
  ResultResponse<Void> addClub(ClubDto clubDto) throws IOException;

  // 선호 구단 등록
  ResultResponse<Void> addSupportClub(Long userId, String clubName);

  // 선호 구단 삭제
  ResultResponse<Void> deleteSupportClub(Long userId, String clubName);
}
