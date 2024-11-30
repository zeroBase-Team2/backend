package com.service.sport_companion.api.controller;

import com.service.sport_companion.api.service.FixturesService;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fixture")
public class FixturesController {

  private final FixturesService fixturesService;

  // 추후 변경할 예정
  @GetMapping("/crawl")
  private ResponseEntity<ResultResponse<Void>> crawlFixtures(@RequestParam("year") String year) {

    ResultResponse<Void> response = fixturesService.crawlFixtures(year);
    return new ResponseEntity<>(response, response.getStatus());

  }



}
