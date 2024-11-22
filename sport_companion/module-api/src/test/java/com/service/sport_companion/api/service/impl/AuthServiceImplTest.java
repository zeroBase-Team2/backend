package com.service.sport_companion.api.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.service.sport_companion.api.auth.jwt.JwtUtil;
import com.service.sport_companion.api.auth.oauth.KakaoAuthHandler;
import com.service.sport_companion.api.component.UserHandler;
import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.auth.KakaoUserDetailsDTO;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.model.type.TokenType;
import com.service.sport_companion.domain.model.type.UrlType;
import com.service.sport_companion.domain.model.type.UserRole;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

  @Mock
  private UserHandler userHandler;

  @Mock
  private JwtUtil jwtUtil;

  @Mock
  private HttpServletResponse response;

  @Mock
  private KakaoAuthHandler kakaoAuthHandler;

  @InjectMocks
  private AuthServiceImpl authServiceImpl;

  private static final String EMAIL = "test@email.com";
  private static final String NICKNAME = "nickname";
  private static final String ACCESS = "access-token";
  private static final String REFRESH = "refresh-token";
  private static final String CODE = "kakaoCode";
  private static final String KAKAO_TOKEN = "kakao-token";
  private static final String KAKAO_PROVIDER = "kakao";
  private static final String KAKAO_PROVIDER_ID = "kakao_provider_id";

  private KakaoUserDetailsDTO kakaoUserDetails;
  private UsersEntity user;

  @BeforeEach
  void setUp() {
    Map<String, Object> kakaoAccount = new HashMap<>();
    kakaoAccount.put("email", EMAIL);

    Map<String, Object> properties = new HashMap<>();
    properties.put("nickname", NICKNAME);

    Map<String, Object> attributes = new HashMap<>();
    attributes.put("id", "providerId");
    attributes.put("kakao_account", kakaoAccount);
    attributes.put("properties", properties);

    kakaoUserDetails = new KakaoUserDetailsDTO(attributes);

    user = UsersEntity.builder()
        .userId(1L)
        .email(EMAIL)
        .nickname(NICKNAME)
        .provider(KAKAO_PROVIDER)
        .providerId(KAKAO_PROVIDER_ID)
        .role(UserRole.USER)
        .createdAt(LocalDateTime.now())
        .build();
  }

  @Test
  @DisplayName("OAuth2 카카오 회원가입 페이지 리다이렉트 성공")
  void redirectToKakaoSignUpPageSuccessfully() {
    // given
    when(kakaoAuthHandler.getAccessToken(CODE)).thenReturn(KAKAO_TOKEN);
    when(kakaoAuthHandler.getUserDetails(KAKAO_TOKEN)).thenReturn(kakaoUserDetails);
    when(userHandler.findUserByUserInfo(kakaoUserDetails)).thenReturn(null);
    when(userHandler.getRandomNickname(1)).thenReturn(NICKNAME);

    // when
    String resultResponse = authServiceImpl.oAuthForKakao(CODE, response);

    // then
    String response = UriComponentsBuilder.fromUriString(UrlType.SIGNUP_URL.getUrl())
        .queryParam("email", kakaoUserDetails.getEmail())
        .queryParam("nickname", NICKNAME)
        .queryParam("provider", kakaoUserDetails.getProvider())
        .queryParam("providerId", kakaoUserDetails.getProviderId())
        .build()
        .toUriString();

    assertEquals(response, resultResponse);
  }

  @Test
  @DisplayName("OAuth2 카카오 로그인 성공 후 페이지 리다이렉트 성공")
  void redirectToKakaoLoginPageSuccessfully() {
    // given
    when(kakaoAuthHandler.getAccessToken(CODE)).thenReturn(KAKAO_TOKEN);
    when(kakaoAuthHandler.getUserDetails(KAKAO_TOKEN)).thenReturn(kakaoUserDetails);
    when(userHandler.findUserByUserInfo(kakaoUserDetails)).thenReturn(user);
    when(jwtUtil.createJwt("access", user.getUserId(), user.getRole())).thenReturn(ACCESS);
    when(jwtUtil.createJwt("refresh", user.getUserId(), user.getRole())).thenReturn(REFRESH);

    // when
    String resultResponse = authServiceImpl.oAuthForKakao(CODE, response);

    // then
    String response = UriComponentsBuilder.fromUriString(UrlType.LOGIN_URL.getUrl())
        .queryParam(TokenType.ACCESS.getValue(), ACCESS)
        .queryParam(TokenType.REFRESH.getValue(), REFRESH)
        .build()
        .toUriString();

    assertEquals(response, resultResponse);
  }


  @Test
  @DisplayName("OAuth2 카카오 Access 토큰 발급 실패")
  void failedGetAccessToken() {
    // given
    when(kakaoAuthHandler.getAccessToken(CODE))
        .thenThrow(new GlobalException(FailedResultType.ACCESS_TOKEN_RETRIEVAL));

    // when & then
    assertThrows(GlobalException.class, () -> authServiceImpl.oAuthForKakao(CODE, response));
  }


  @Test
  @DisplayName("OAuth2 카카오 사용자 정보 반환 실패")
  void failedGetUserDetails() {
    // given
    when(kakaoAuthHandler.getAccessToken(CODE)).thenReturn(KAKAO_TOKEN);
    when(kakaoAuthHandler.getUserDetails(KAKAO_TOKEN))
        .thenThrow(new GlobalException(FailedResultType.USER_INFO_RETRIEVAL));

    // when & then
    assertThrows(GlobalException.class, () -> authServiceImpl.oAuthForKakao(CODE, response));
  }


  @Test
  @DisplayName("OAuth2 다른 소셜 사이트로 회원가입 한 경우")
  void failedFindUserByUserInfo() {
    // given
    when(kakaoAuthHandler.getAccessToken(CODE)).thenReturn(KAKAO_TOKEN);
    when(kakaoAuthHandler.getUserDetails(KAKAO_TOKEN)).thenReturn(kakaoUserDetails);
    when(userHandler.findUserByUserInfo(kakaoUserDetails))
        .thenThrow(new GlobalException(FailedResultType.EMAIL_ALREADY_USED));

    // when & then
    assertThrows(GlobalException.class, () -> authServiceImpl.oAuthForKakao(CODE, response));
  }


  @Test
  @DisplayName("OAuth2 랜덤 닉네임 발급 실패")
  void failedGetRandomNickname() {
    // given
    when(kakaoAuthHandler.getAccessToken(CODE)).thenReturn(KAKAO_TOKEN);
    when(kakaoAuthHandler.getUserDetails(KAKAO_TOKEN)).thenReturn(kakaoUserDetails);
    when(userHandler.findUserByUserInfo(kakaoUserDetails)).thenReturn(null);
    when(userHandler.getRandomNickname(anyInt()))
        .thenThrow(new GlobalException(FailedResultType.UNIQUE_NICKNAME_FAILED));

    // when & then
    assertThrows(GlobalException.class, () -> authServiceImpl.oAuthForKakao(CODE, response));
  }
}