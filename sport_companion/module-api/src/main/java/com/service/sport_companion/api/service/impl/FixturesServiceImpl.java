package com.service.sport_companion.api.service.impl;

import com.service.sport_companion.api.component.crawl.CrawlFixtures;
import com.service.sport_companion.api.service.FixturesService;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FixturesServiceImpl implements FixturesService {

  private final CrawlFixtures crawlFixtures;

  @Override
  public ResultResponse crawlFixtures(String year) {
    crawlFixtures.crawlFixtures(year);

    return ResultResponse.of(SuccessResultType.SUCCESS_CRAWL_FIXTURE);
  }
}
