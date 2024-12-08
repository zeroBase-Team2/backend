package com.service.sport_companion.api.controller;

import com.service.sport_companion.api.service.SupportClubService;
import com.service.sport_companion.domain.model.annotation.CallUser;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.support.SupportClub;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/support")
public class SupportClubController {

  private final SupportClubService supportClubService;

  // 선호 구단 조회
  @GetMapping
  public ResponseEntity<ResultResponse<SupportClub>> getSupportClub(@CallUser Long userId) {

    ResultResponse<SupportClub> response = supportClubService.getSupportClub(userId);
    return new ResponseEntity<>(response, response.getStatus());
  }

  // 선호구단 등록
  @PostMapping("/{clubName}")
  public ResponseEntity<ResultResponse<Void>> addSupportClub(@CallUser Long userId,
      @PathVariable("clubName") String clubName) {

    ResultResponse<Void> response = supportClubService.addSupportClub(userId, clubName);
    return new ResponseEntity<>(response, response.getStatus());
  }

  // 선호구단 삭제
  @DeleteMapping("/{clubName}")
  public ResponseEntity<ResultResponse<Void>> deleteSupportClub(@CallUser Long userId,
      @PathVariable("clubName") String clubName) {

    ResultResponse<Void> response = supportClubService.deleteSupportClub(userId, clubName);
    return new ResponseEntity<>(response, response.getStatus());
  }

}
