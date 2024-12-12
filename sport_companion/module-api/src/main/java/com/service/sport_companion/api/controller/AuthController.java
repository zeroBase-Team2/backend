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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  /**
   * 카카오 OAuth 로그인 및 회원가입 여부 확인
   *
   * @param response HTTP 응답 객체
   * @param kakaoCode 카카오 인가 코드 DTO
   */
  @PostMapping("/kakao")
  public ResponseEntity<ResultResponse<?>> oAuthForKakao(
      HttpServletResponse response,
      @RequestBody @Valid KakaoCodeDto kakaoCode
  ) {
    log.info("카카오 OAuth 요청 시작 - code: {}", kakaoCode.getCode());

    ResultResponse<?> resultResponse = authService.oAuthForKakao(kakaoCode.getCode(), response);

    log.info("카카오 OAuth 요청 완료 - 상태: {}", resultResponse.getStatus());
    return new ResponseEntity<>(resultResponse, resultResponse.getStatus());
  }


  /**
   * 닉네임 중복 검증
   *
   * @param nickname 검증할 닉네임
   */
  @GetMapping("/check-nickname/{nickname}")
  public ResponseEntity<ResultResponse<Boolean>> checkNickname(
      @NotBlank(message = "닉네임을 입력해 주세요.") @PathVariable("nickname") String nickname
  ) {
    log.info("닉네임 중복 확인 요청 - nickname: {}", nickname);

    ResultResponse<Boolean> result = authService.checkNickname(nickname);

    log.info("닉네임 중복 확인 완료 - 사용 가능 여부: {}", result.getData());
    return ResponseEntity.ok(result);
  }


  /**
   * 회원가입 처리
   *
   * @param signUpDto 회원가입 요청 데이터
   */
  @PostMapping("/signup")
  public ResponseEntity<ResultResponse<Void>> signup(@RequestBody @Valid SignUpDto signUpDto) {
    log.info("회원가입 요청 시작 - providerId: {}", signUpDto.getProviderId());

    ResultResponse<Void> response = authService.signup(signUpDto);

    log.info("회원가입 요청 완료 - 상태: {}", response.getStatus());
    return new ResponseEntity<>(response, response.getStatus());
  }

  /**
   * Access Token 재발급
   *
   * @param request HTTP 요청 객체
   * @param response HTTP 응답 객체
   */
  @GetMapping("/reissue-token")
  public ResponseEntity<ResultResponse<Void>> reissueToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) {
    log.info("토큰 재발급 요청 시작");

    ResultResponse<Void> resultResponse = authService.reissueToken(request, response);

    log.info("토큰 재발급 요청 완료 - 상태: {}", resultResponse.getStatus());
    return new ResponseEntity<>(resultResponse, resultResponse.getStatus());
  }
}