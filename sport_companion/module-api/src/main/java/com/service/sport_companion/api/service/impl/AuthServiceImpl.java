package com.service.sport_companion.api.service.impl;

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
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final KakaoAuthHandler kakaoAuthHandler;
  private final UserHandler userHandler;
  private final JwtUtil jwtUtil;

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

      String nickname = userHandler.getRandomNickname(1);

      return UriComponentsBuilder.fromUriString(UrlType.SIGNUP_URL.getUrl())
          .queryParam("email", userInfo.getEmail())
          .queryParam("nickname", nickname)
          .queryParam("provider", userInfo.getProvider())
          .queryParam("providerId", userInfo.getProviderId())
          .build()
          .toUriString();
    }

    // 가입 이력이 있는 경우 로그인 진행
    String access = jwtUtil.createJwt("access", user.getUserId(), user.getRole());
    String refresh = jwtUtil.createJwt("refresh", user.getUserId(), user.getRole());

    return UriComponentsBuilder.fromUriString(UrlType.FRONT_LOCAL_URL.getUrl())
        .queryParam(TokenType.ACCESS.getValue(), access)
        .queryParam(TokenType.REFRESH.getValue(), refresh)
        .build()
        .toUriString();
  }

  @Override
  public ResultResponse checkNickname(String nickname) {
    boolean isValidNickname = !userHandler.existsByNickname(nickname);

    SuccessResultType resultType = (isValidNickname) ?
      SuccessResultType.AVAILABLE_NICKNAME : SuccessResultType.UNAVAILABLE_NICKNAME;

    return new ResultResponse(resultType, isValidNickname);
  }
}
