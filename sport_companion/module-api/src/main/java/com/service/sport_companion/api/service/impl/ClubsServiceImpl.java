package com.service.sport_companion.api.service.impl;

import com.service.sport_companion.api.component.club.ClubsHandler;
import com.service.sport_companion.api.component.S3Handler;
import com.service.sport_companion.api.service.ClubsService;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.model.dto.request.club.ClubDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.clubs.Clubs;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubsServiceImpl implements ClubsService {

  private final ClubsHandler clubsHandler;
  private final S3Handler s3Handler;


  // 모든 구단 조회
  @Override
  public ResultResponse<List<Clubs>> getAllClubList() {
    // 다른 종목 추가시 수정할 예정
    List<Clubs> clubList = clubsHandler.getAllClubList();

    return new ResultResponse<>(SuccessResultType.SUCCESS_GET_ALL_CLUBS_LIST, clubList);
  }

  // 구단 등록
  @Override
  public ResultResponse<Void> addClub(ClubDto clubDto) throws IOException {
    String emblemImg = s3Handler.upload(clubDto.getFile());

    clubsHandler.saveClub(ClubsEntity.builder()
        .clubName(clubDto.getName())
        .clubStadium(clubDto.getStadium())
        .stadiumAddress(clubDto.getStadiumAddress())
        .reservationSite(clubDto.getReservationSite())
        .emblemImg(emblemImg)
        .build());

    return ResultResponse.of(SuccessResultType.SUCCESS_ADD_CLUB);
  }

}
