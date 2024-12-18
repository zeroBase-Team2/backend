package com.service.sport_companion.api.service.impl;

import com.service.sport_companion.api.auth.jwt.JwtUtil;
import com.service.sport_companion.api.auth.oauth.KakaoAuthHandler;
import com.service.sport_companion.api.component.RedisHandler;
import com.service.sport_companion.api.component.UserHandler;
import com.service.sport_companion.api.component.club.ClubsFacade;
import com.service.sport_companion.api.service.AuthService;
import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.SignUpDataEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.auth.KakaoUserDetailsDTO;
import com.service.sport_companion.domain.model.dto.request.auth.SignUpDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.auth.SignUpData;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import com.service.sport_companion.domain.model.type.TokenType;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final KakaoAuthHandler kakaoAuthHandler;
  private final UserHandler userHandler;
  private final RedisHandler redisHandler;
  private final ClubsFacade clubsFacade;
  private final JwtUtil jwtUtil;



  /**
   * 카카오 OAuth 로그인 및 회원가입 처리
   *
   * @param code 인가 코드
   * @param response HTTP 응답 객체
   */
  @Override
  public ResultResponse<?> oAuthForKakao(String code, HttpServletResponse response) {
    log.info("카카오 OAuth 처리 시작");

    String accessToken = kakaoAuthHandler.getAccessToken(code);
    KakaoUserDetailsDTO userInfo = kakaoAuthHandler.getUserDetails(accessToken);

    // 기존 회원 조회
    UsersEntity user = userHandler.findUserByUserInfo(userInfo);

    // 신규 사용자 회원가입 필요
    if (user == null) {
      log.info("회원가입 필요 - providerId: {}", userInfo.getProviderId());

      String nickname = userHandler.getRandomNickname(1);
      SignUpData signUpData = new SignUpData(userInfo.getProviderId(), nickname);

      userHandler.saveSingUpCacheData(userInfo);

      return new ResultResponse<>(SuccessResultType.SUCCESS_SIGNUP_REQUIRED, signUpData);
    }

    // 기존 회원 - 로그인 성공
    log.info("로그인 성공 - userId: {}", user.getUserId());

    String access = jwtUtil.createJwt("access", user.getUserId(), user.getNickname(), user.getRole());
    String refresh = jwtUtil.createJwt("refresh", user.getUserId(), user.getNickname(),user.getRole());

    response.addHeader(TokenType.ACCESS.getValue(), access);
    response.addHeader(TokenType.REFRESH.getValue(), refresh);

    return ResultResponse.of(SuccessResultType.SUCCESS_LOGIN);
  }


  /**
   * 회원가입 처리
   *
   * @param signUpDto 회원가입 요청 데이터
   */
  @Override
  public ResultResponse<Void> signup(SignUpDto signUpDto) {
    log.info("회원가입 처리 시작 - providerId: {}", signUpDto.getProviderId());

    SignUpDataEntity signUpData = redisHandler.getSignUpDataByProviderId(signUpDto.getProviderId());
    UsersEntity user = SignUpDto.of(signUpData, signUpDto.getNickname());

    userHandler.saveUser(user);

    if (signUpDto.getClubName() != null) {
      clubsFacade.saveSupportedClub(user, signUpDto.getClubName());
    }

    log.info("회원가입 성공 - userId: {}", user.getUserId());
    return ResultResponse.of(SuccessResultType.SUCCESS_SIGNUP);
  }

  /**
   * 닉네임 중복 확인
   *
   * @param nickname 닉네임
   */
  @Override
  public ResultResponse<Boolean> checkNickname(String nickname) {
    log.info("닉네임 중복 확인 - nickname: {}", nickname);

    boolean isValidNickname = !userHandler.existsByNickname(nickname);
    SuccessResultType resultType = isValidNickname ?
        SuccessResultType.AVAILABLE_NICKNAME : SuccessResultType.UNAVAILABLE_NICKNAME;

    log.info("닉네임 '{}' 사용 가능 여부: {}", nickname, isValidNickname);
    return new ResultResponse<>(resultType, isValidNickname);
  }


  /**
   * 토큰 재발급
   *
   * @param request HTTP 요청 객체
   * @param response HTTP 응답 객체
   */
  @Override
  public ResultResponse<Void> reissueToken(HttpServletRequest request, HttpServletResponse response) {
    log.info("토큰 재발급 처리 시작");

    // 쿠키 검증
    String refreshToken = extractTokenFromCookies(request.getCookies());

    if (refreshToken == null || jwtUtil.isExpired(refreshToken)) {
      log.warn("유효하지 않거나 만료된 Refresh Token");
      throw new GlobalException(FailedResultType.REFRESH_TOKEN_IS_EXPIRED);
    }

    Long userId = jwtUtil.getUserId(refreshToken);
    UsersEntity user = userHandler.findByUserId(userId);

    String access = jwtUtil.createJwt("access", user.getUserId(), user.getNickname(), user.getRole());
    response.addHeader(TokenType.ACCESS.getValue(), access);

    log.info("토큰 재발급 성공 - userId: {}", user.getUserId());
    return ResultResponse.of(SuccessResultType.SUCCESS_REISSUE_TOKEN);
  }


  /**
   * 쿠키에서 토큰 추출
   *
   * @param cookies 쿠키 배열
   */
  private String extractTokenFromCookies(Cookie[] cookies) {
    if (cookies == null || cookies.length == 0) {
      log.warn("쿠키가 비어 있습니다.");
      throw new GlobalException(FailedResultType.COOKIE_IS_NULL);
    }

    return Arrays.stream(cookies)
        .filter(cookie -> TokenType.REFRESH.getValue().equals(cookie.getName()))
        .map(Cookie::getValue)
        .findFirst()
        .orElse(null);
  }

  @Override
  public ResultResponse<Void> deleteUser(Long userId) {
    UsersEntity user = userHandler.findByUserId(userId);

    kakaoAuthHandler.unlinkUser(user.getProviderId());
    userHandler.deleteAllUserData(user);

    return ResultResponse.of(SuccessResultType.SUCCESS_DELETE_USERINFO);
  }
}