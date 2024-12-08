package com.service.sport_companion.api.controller;

import com.service.sport_companion.api.service.AuthService;
import com.service.sport_companion.domain.model.auth.KakaoCodeDto;
import com.service.sport_companion.domain.model.dto.request.auth.SignUpDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  // 카카오 로그인 or 회원 가입 여부 확인
  @PostMapping("/kakao")
  public ResponseEntity<ResultResponse<?>> oAuthForKakao(HttpServletResponse response,
      @RequestBody @Valid KakaoCodeDto kakaoCode) {

    ResultResponse<?> resultResponse = authService.oAuthForKakao(kakaoCode.getCode(), response);
    return new ResponseEntity<>(resultResponse, resultResponse.getStatus());
  }

  // 닉네임 중복 검증
  @GetMapping("/check-nickname/{nickname}")
  public ResponseEntity<ResultResponse<Boolean>> checkNickname(
    @NotBlank(message = "닉네임을 입력해 주세요.") @PathVariable("nickname") String nickname) {

    return ResponseEntity.ok(authService.checkNickname(nickname));
  }

  // 회원 가입
  @PostMapping("/signup")
  public ResponseEntity<ResultResponse<Void>> signup(@RequestBody @Valid SignUpDto signUpDto) {

    ResultResponse<Void> response = authService.signup(signUpDto);
    return new ResponseEntity<>(response, response.getStatus());
  }

  // Access Token 재발급
  @GetMapping("/reissue-token")
  public ResponseEntity<ResultResponse<Void>> reissueToken(
      HttpServletRequest request, HttpServletResponse response) {

    ResultResponse<Void> resultResponse = authService.reissueToken(request, response);
    return new ResponseEntity<>(resultResponse, resultResponse.getStatus());
  }
}
