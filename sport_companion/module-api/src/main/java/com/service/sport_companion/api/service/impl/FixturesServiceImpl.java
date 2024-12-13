package com.service.sport_companion.api.service.impl;

import com.service.sport_companion.api.component.club.ClubsFacade;
import com.service.sport_companion.api.component.crawl.CrawlFixtures;
import com.service.sport_companion.api.service.FixturesService;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.fixtures.FixtureDetails;
import com.service.sport_companion.domain.model.dto.response.fixtures.Fixtures;
import com.service.sport_companion.domain.model.type.SuccessResultType;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FixturesServiceImpl implements FixturesService {

  private final CrawlFixtures crawlFixtures;
  private final ClubsFacade clubsFacade;


  /**
   * 경기 일정 데이터를 크롤링하여 저장
   * @param year 크롤링 대상 연도
   */
  @Override
  public ResultResponse<Void> crawlFixtures(String year) {
    log.info("저장할 경기 일정 년도: {}", year);

    crawlFixtures.crawlFixtures(year);

    log.info("{} 년도 경기 일정 저장 성공", year);
    return ResultResponse.of(SuccessResultType.SUCCESS_CRAWL_FIXTURE);
  }


  /**
   * 사용자의 선호 구단에 따른 경기 일정 조회
   *
   * @param userId 사용자 ID
   * @param date   조회 날짜 (yyyy-MM-dd 형식)
   */
  @Override
  public ResultResponse<List<Fixtures>> getFixtureList(Long userId, String date) {
    log.info("조회할 경기 날짜: {}", date);

    LocalDate fixtureDate = LocalDate.parse(date);

    List<Fixtures> fixturesList = clubsFacade.getFixtureList(fixtureDate, userId);

    log.info("{} 년도 경기 일정 조회 성공", date);
    return new ResultResponse<>(SuccessResultType.SUCCESS_GET_ALL_FIXTURES, fixturesList);
  }

  /**
   * 특정 구단의 경기 정보를 조회
   *
   * @param clubName 구단 명
   * @param date   조회 날짜 (yyyy-MM-dd 형식)
   */
  @Override
  public ResultResponse<List<Fixtures>> getClubFixtureList(String clubName, String date) {
    log.info("조회할 구단 : {}, 경기 날짜: {}",clubName, date);

    LocalDate fixtureDate = LocalDate.parse(date);

    List<Fixtures> fixturesList = clubsFacade.getClubFixtureList(clubName, fixtureDate);

    log.info("{} 년도 {} 경기 일정 조회 성공",clubName, date);
    return new ResultResponse<>(SuccessResultType.SUCCESS_GET_CLUB_FIXTURES, fixturesList);
  }

  /**
   * 특정 경기의 상세 정보를 조회
   *
   * @param fixtureId 경기 ID
   */
  @Override
  public ResultResponse<FixtureDetails> getFixtureDetails(Long fixtureId) {
    log.info("상세 정보를 조회할 경기 ID : {}", fixtureId);

    FixtureDetails fixtureDetails = clubsFacade.getFixtureDetails(fixtureId);

    log.info("{} 번 경기 조회 성공", fixtureId);
    return new ResultResponse<>(SuccessResultType.SUCCESS_GET_FIXTURES_DETAILS, fixtureDetails);
  }
}