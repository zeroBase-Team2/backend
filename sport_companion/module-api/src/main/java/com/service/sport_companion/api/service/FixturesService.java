package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.fixtures.Fixtures;
import java.util.List;

public interface FixturesService {

  ResultResponse<Void> crawlFixtures(String year);

  ResultResponse<List<Fixtures>> getFixtureList(Long userId, String year, String month, String day,
      String season);
}
