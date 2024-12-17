package com.service.sport_companion.api.service.impl;

import com.service.sport_companion.api.component.UserHandler;
import com.service.sport_companion.api.component.club.ClubsFacade;
import com.service.sport_companion.api.component.S3Handler;
import com.service.sport_companion.api.service.ClubsService;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.dto.request.club.ClubDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.clubs.Clubs;
import com.service.sport_companion.domain.model.dto.response.support.SupportClub;
import com.service.sport_companion.domain.model.type.SuccessResultType;

import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubsServiceImpl implements ClubsService {

  private final ClubsFacade clubsFacade;
  private final S3Handler s3Handler;
  private final UserHandler userHandler;

  /**
   * 모든 구단 정보 조회
   */
  @Override
  public ResultResponse<List<Clubs>> getAllClubList() {
    log.info(">>> 모든 구단 정보 조회");

    List<Clubs> clubList = clubsFacade.getAllClubList();

    log.info(">>> 모든 구단 정보 조회 성공");
    return new ResultResponse<>(SuccessResultType.SUCCESS_GET_ALL_CLUBS_LIST, clubList);
  }

  /**
   * 새로운 구단 등록
   *
   * @param clubDto 구단 정보
   * @throws IOException 파일 업로드 실패 시 예외 발생
   */
  @Override
  public ResultResponse<Void> addClub(ClubDto clubDto) throws IOException {
    log.info(">>> 등록할 구단 명: {}", clubDto.getName());

    String emblemImg = s3Handler.upload(clubDto.getFile());
    ClubsEntity clubEntity = ClubDto.of(clubDto, emblemImg);

    clubsFacade.saveClub(clubEntity);

    log.info("{} 구단 등록 성공", clubDto.getName());
    return ResultResponse.of(SuccessResultType.SUCCESS_ADD_CLUB);
  }

  /**
   * 사용자 선호 구단 등록
   *
   * @param userId   사용자 ID
   * @param clubName 구단 명
   */
  @Override
  public ResultResponse<Void> addSupportClub(Long userId, String clubName) {
    log.info("선호 구단을 위한 사용자 ID : {}, 구단 명 : {}", userId, clubName);

    UsersEntity user = userHandler.findByUserId(userId);
    clubsFacade.saveSupportedClub(user, clubName);

    log.info("{} 번 사용자 {} 구단 등록 성공", userId, clubName);
    return ResultResponse.of(SuccessResultType.SUCCESS_ADD_SUPPORT_CLUB);
  }

  /**
   * 사용자 선호 구단 삭제
   *
   * @param userId   사용자 ID
   * @param clubName 구단 명
   */
  @Override
  public ResultResponse<Void> deleteSupportClub(Long userId, String clubName) {
    log.info("선호 구단 삭제를 위한 사용자 ID : {}, 구단 명 : {}", userId, clubName);

    clubsFacade.deleteSupportClubs(userId, clubName);

    log.info("{} 번 사용자 {} 구단 삭제 성공", userId, clubName);
    return ResultResponse.of(SuccessResultType.SUCCESS_DELETE_SUPPORT_CLUB);
  }
}