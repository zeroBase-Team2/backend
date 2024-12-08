package com.service.sport_companion.api.service.impl;

import com.service.sport_companion.api.component.club.FixtureHandler;
import com.service.sport_companion.api.component.club.SupportedClubsHandler;
import com.service.sport_companion.api.component.crawl.CrawlFixtures;
import com.service.sport_companion.api.service.FixturesService;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.fixtures.Fixtures;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FixturesServiceImpl implements FixturesService {

  private final CrawlFixtures crawlFixtures;
  private final FixtureHandler fixtureHandler;
  private final SupportedClubsHandler supportedClubsHandler;

  // 경기 일정 크롤링
  @Override
  public ResultResponse<Void> crawlFixtures(String year) {
    crawlFixtures.crawlFixtures(year);

    return ResultResponse.of(SuccessResultType.SUCCESS_CRAWL_FIXTURE);
  }

  // 경기 일정 조회
  @Override
  public ResultResponse<List<Fixtures>> getFixtureList(Long userId, String year, String month, String day) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = year + "-" + month + "-" + day;
    LocalDate fixtureDate = LocalDate.parse(formattedDate, formatter);

    ClubsEntity clubs = (userId != null) ? supportedClubsHandler.findSupportClubsByUserId(userId) : null;

    // 필요한 데이터를 가져오기
    List<Fixtures> fixturesList = (clubs != null)
        ? fixtureHandler.getSupportClubFixturesList(fixtureDate, clubs)
        : fixtureHandler.getAllFixturesList(fixtureDate);

    return new ResultResponse<>(SuccessResultType.SUCCESS_GET_ALL_FIXTURES, fixturesList);
  }
}
