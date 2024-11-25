package com.service.sport_companion.api.service.impl;

import static com.service.sport_companion.api.utils.HttpCookieUtil.addCookieToResponse;

import com.service.sport_companion.api.auth.jwt.JwtUtil;
import com.service.sport_companion.api.auth.oauth.KakaoAuthHandler;
import com.service.sport_companion.api.component.UserHandler;
import com.service.sport_companion.api.service.AuthService;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.auth.KakaoUserDetailsDTO;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import com.service.sport_companion.domain.model.type.TokenType;
import com.service.sport_companion.domain.model.type.UrlType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final KakaoAuthHandler kakaoAuthHandler;
  private final UserHandler userHandler;
  private final JwtUtil jwtUtil;

  private final long TEN_MINUTES = 10 * 60;
  private final long ONE_DAY = 24 * 60 * 60;

  @Override
  public String oAuthForKakao(String code, HttpServletResponse response) {
    // "인가 코드"로 "액세스 토큰" 요청
    String accessToken = kakaoAuthHandler.getAccessToken(code);

    // 토큰으로 사용자 정보 요청
    KakaoUserDetailsDTO userInfo = kakaoAuthHandler.getUserDetails(accessToken);

    // 회원 가입 여부 확인
    UsersEntity user = userHandler.findUserByUserInfo(userInfo);

    // 가입 이력이 없는 경우 추가 데이터 입력을 위해 리다이렉트
    if(user == null) {
      return handleSignup(userInfo, response);
    }

    // 로그인 성공 페이지로 리다이렉트
    return handleLogin(user, response);
  }

  // 회원가입 처리
  private String handleSignup(KakaoUserDetailsDTO userInfo, HttpServletResponse response) {
    String nickname = userHandler.getRandomNickname(1);

    String signUpData = jwtUtil.createSignupData(userInfo.getProviderId(), nickname);

    // 쿠키 생성 및 응답 헤더 추가
    addCookieToResponse(response, "signUpData", signUpData, TEN_MINUTES);

    userHandler.saveSingUpCacheData(userInfo);
    return UrlType.SIGNUP_URL.getUrl();
  }

  // 로그인 처리
  private String handleLogin(UsersEntity user, HttpServletResponse response) {
    String access = jwtUtil.createJwt("access", user.getUserId(), user.getRole());
    String refresh = jwtUtil.createJwt("refresh", user.getUserId(), user.getRole());

    // 쿠키 생성 및 응답 헤더 추가
    addCookieToResponse(response, TokenType.ACCESS.getValue(), access, TEN_MINUTES);
    addCookieToResponse(response, TokenType.REFRESH.getValue(), refresh, ONE_DAY);

    return UrlType.FRONT_LOCAL_URL.getUrl();
  }

  @Override
  public ResultResponse checkNickname(String nickname) {
    boolean isValidNickname = !userHandler.existsByNickname(nickname);

    SuccessResultType resultType = (isValidNickname) ?
      SuccessResultType.AVAILABLE_NICKNAME : SuccessResultType.UNAVAILABLE_NICKNAME;

    return new ResultResponse(resultType, isValidNickname);
  }
}
