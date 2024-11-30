package com.service.sport_companion.api.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.service.sport_companion.api.component.crawl.CrawlFixtures;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FixturesServiceImplTest {

  @Mock
  private CrawlFixtures crawlFixtures;

  @InjectMocks
  private FixturesServiceImpl fixturesService;

  private final String YEAR = "2024";

  @Test
  @DisplayName("년도 별 경기 일정 크롤링 성공")
  void crawlFixturesSuccessfully() {
    // given
    doNothing().when(crawlFixtures).crawlFixtures(YEAR);

    // when
    ResultResponse resultResponse = fixturesService.crawlFixtures(YEAR);

    // then
    assertEquals(SuccessResultType.SUCCESS_CRAWL_FIXTURE.getStatus(), resultResponse.getStatus());
  }

}