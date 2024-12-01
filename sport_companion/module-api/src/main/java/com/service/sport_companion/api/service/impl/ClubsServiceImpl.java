package com.service.sport_companion.api.service.impl;

import com.service.sport_companion.api.component.ClubsHandler;
import com.service.sport_companion.api.component.ReservationSiteHandler;
import com.service.sport_companion.api.component.S3Handler;
import com.service.sport_companion.api.component.SportHandler;
import com.service.sport_companion.api.service.ClubsService;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.ReservationSiteEntity;
import com.service.sport_companion.domain.entity.SportsEntity;
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
  private final SportHandler sportHandler;
  private final ReservationSiteHandler reservationSiteHandler;


  // 모든 구단 조회
  @Override
  public ResultResponse<List<Clubs>> getAllClubList() {
    // 다른 종목 추가시 수정할 예정
    List<Clubs> clubList = clubsHandler.getAllClubList();

    return new ResultResponse<>(SuccessResultType.SUCCESS_GET_ALL_CLUBS_LIST, clubList);
  }

  @Override
  public ResultResponse<Void> addClub(ClubDto clubDto) throws IOException {
    String emblemImg = s3Handler.upload(clubDto.getFile());

    SportsEntity sport = sportHandler.findBySportName(clubDto.getSportsName());
    ReservationSiteEntity site = reservationSiteHandler.findBySiteName(clubDto.getSiteName());

    clubsHandler.saveClub(ClubsEntity.builder()
        .sports(sport)
        .clubName(clubDto.getClubName())
        .clubStadium(clubDto.getClubStadium())
        .reservationSite(site)
        .emblemImg(emblemImg)
        .build());

    return ResultResponse.of(SuccessResultType.SUCCESS_ADD_CLUB);
  }

}
