package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.response.ResultResponse;

public interface FixturesService {

  ResultResponse crawlFixtures(String year);
}
