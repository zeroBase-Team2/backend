package com.service.sport_companion.api.controller;

import com.service.sport_companion.api.service.UsersService;
import com.service.sport_companion.domain.model.annotation.CallUser;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.user.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersController {

  private final UsersService usersService;

  // 사용자 정보 조회
  @GetMapping
  public ResponseEntity<ResultResponse<UserInfo>> getUserInfo(@CallUser Long userId) {

    ResultResponse<UserInfo> response = usersService.getUserInfo(userId);
    return new ResponseEntity<>(response, response.getStatus());
  }

  // 사용자 정보 (닉네임 수정)
  @PutMapping("/{nickname}")
  public ResponseEntity<ResultResponse<Void>> updateUserInfo(
      @CallUser Long userId,
      @PathVariable("nickname") String nickname) {

    ResultResponse<Void> response = usersService.updateUserInfo(userId, nickname);
    return new ResponseEntity<>(response, response.getStatus());
  }

}
