package com.service.sport_companion.api.controller;

import com.service.sport_companion.api.service.UsersService;
import com.service.sport_companion.domain.model.annotation.CallUser;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersController {

  private final UsersService usersService;

  @PutMapping("/{nickname}")
  public ResponseEntity<ResultResponse<Void>> updateUserInfo(
      @CallUser Long userId,
      @PathVariable("nickname") String nickname) {

    ResultResponse<Void> response = usersService.updateUserInfo(userId, nickname);
    return new ResponseEntity<>(response, response.getStatus());
  }

}
