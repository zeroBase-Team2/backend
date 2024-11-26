package com.service.sport_companion.api.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.service.sport_companion.api.auth.jwt.JwtUtil;
import com.service.sport_companion.api.auth.oauth.KakaoAuthHandler;
import com.service.sport_companion.api.component.ClubsHandler;
import com.service.sport_companion.api.component.RedisHandler;
import com.service.sport_companion.api.component.SupportedClubsHandler;
import com.service.sport_companion.api.component.UserHandler;
import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.SignUpDataEntity;
import com.service.sport_companion.domain.entity.SupportedClubsEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.auth.KakaoUserDetailsDTO;
import com.service.sport_companion.domain.model.dto.request.auth.SignUpDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import com.service.sport_companion.domain.model.type.UrlType;
import com.service.sport_companion.domain.model.type.UserRole;
import jakarta.servlet.http.HttpServletRequest;
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

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

  @Mock
  private UserHandler userHandler;

  @Mock
  private JwtUtil jwtUtil;

  @Mock
  private HttpServletResponse response;

  @Mock
  private HttpServletRequest request;

  @Mock
  private RedisHandler redisHandler;

  @Mock
  private ClubsHandler clubsHandler;

  @Mock
  private SupportedClubsHandler supportedClubsHandler;

  @Mock
  private KakaoAuthHandler kakaoAuthHandler;

  @InjectMocks
  private AuthServiceImpl authServiceImpl;

  private static final String EMAIL = "test@email.com";
  private static final String NICKNAME = "nickname";
  private static final String CODE = "kakaoCode";
  private static final String KAKAO_TOKEN = "kakao-token";
  private static final String KAKAO_PROVIDER = "kakao";
  private static final String KAKAO_PROVIDER_ID = "kakao_provider_id";


  private KakaoUserDetailsDTO kakaoUserDetails;
  private UsersEntity user;
  private SignUpDataEntity signUpData;
  private SignUpDto signUpDto;
  private ClubsEntity club;

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

    signUpData = SignUpDataEntity.builder()
        .providerId("providerId123")
        .email("test@email.com")
        .provider("kakao")
        .build();

    signUpDto = new SignUpDto("providerId123", "nickname", "KIA 타이거즈");

    club = ClubsEntity.builder()
        .clubId(1L)
        .clubName("KIA 타이거즈")
        .emblemImg("emblem.png")
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
    String resultResponse = authServiceImpl.oAuthForKakao(CODE, request, response);

    // then
    String response = UrlType.SIGNUP_URL.getUrl();
    assertEquals(response, resultResponse);
    verify(jwtUtil).createSignupData(kakaoUserDetails.getProviderId(), NICKNAME);
    verify(userHandler).saveSingUpCacheData(kakaoUserDetails);
  }

  @Test
  @DisplayName("OAuth2 카카오 로그인 성공 후 페이지 리다이렉트 성공")
  void redirectToKakaoLoginPageSuccessfully() {
    // given
    when(kakaoAuthHandler.getAccessToken(CODE)).thenReturn(KAKAO_TOKEN);
    when(kakaoAuthHandler.getUserDetails(KAKAO_TOKEN)).thenReturn(kakaoUserDetails);
    when(userHandler.findUserByUserInfo(kakaoUserDetails)).thenReturn(user);

    // when
    String resultResponse = authServiceImpl.oAuthForKakao(CODE, request, response);

    // then
    String response = UrlType.FRONT_LOCAL_URL.getUrl();
    assertEquals(response, resultResponse);
    verify(jwtUtil).createJwt("access", user.getUserId(), user.getRole());
    verify(jwtUtil).createJwt("refresh", user.getUserId(), user.getRole());
  }


  @Test
  @DisplayName("OAuth2 카카오 Access 토큰 발급 실패")
  void failedGetAccessToken() {
    // given
    when(kakaoAuthHandler.getAccessToken(CODE))
        .thenThrow(new GlobalException(FailedResultType.ACCESS_TOKEN_RETRIEVAL));

    // when & then
    assertThrows(GlobalException.class, () -> authServiceImpl.oAuthForKakao(CODE, request, response));
  }


  @Test
  @DisplayName("OAuth2 카카오 사용자 정보 반환 실패")
  void failedGetUserDetails() {
    // given
    when(kakaoAuthHandler.getAccessToken(CODE)).thenReturn(KAKAO_TOKEN);
    when(kakaoAuthHandler.getUserDetails(KAKAO_TOKEN))
        .thenThrow(new GlobalException(FailedResultType.USER_INFO_RETRIEVAL));

    // when & then
    assertThrows(GlobalException.class, () -> authServiceImpl.oAuthForKakao(CODE, request, response));
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
    assertThrows(GlobalException.class, () -> authServiceImpl.oAuthForKakao(CODE, request, response));
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
    assertThrows(GlobalException.class, () -> authServiceImpl.oAuthForKakao(CODE, request, response));
  }


  @Test
  @DisplayName("checkNickname : 사용 가능한 닉네임 확인")
  void checkNickname_AvailableNickname() {
    // given
    String nickname = "validNickname";
    boolean isValidNickname = true;

    when(userHandler.existsByNickname(nickname)).thenReturn(!isValidNickname);

    // when
    ResultResponse response = authServiceImpl.checkNickname(nickname);

    // then
    assertEquals(response.getData(), isValidNickname);
    assertEquals(response.getMessage(), "사용 가능한 닉네임입니다.");
  }


  @Test
  @DisplayName("checkNickname : 사용 불가능한(중복) 닉네임 확인")
  void checkNickname_UnavailableNickname() {
    // given
    String nickname = "invalidNickname";
    boolean isValidNickname = false;

    when(userHandler.existsByNickname(nickname)).thenReturn(!isValidNickname);

    // when
    ResultResponse response = authServiceImpl.checkNickname(nickname);

    // then
    assertEquals(response.getData(), isValidNickname);
    assertEquals(response.getMessage(), "이미 사용 중인 닉네임입니다.");
  }

  @Test
  @DisplayName("회원가입 성공")
  void signupSuccessfully() {
    // given
    when(redisHandler.getSignUpDataByProviderId(signUpDto.getProviderId())).thenReturn(signUpData);
    when(clubsHandler.findByClubName(signUpDto.getClubName())).thenReturn(club);

    // when
    ResultResponse response = authServiceImpl.signup(signUpDto);

    // then
    assertEquals(SuccessResultType.SUCCESS_SIGNUP.getStatus(), response.getStatus());
    verify(redisHandler).getSignUpDataByProviderId(signUpDto.getProviderId());
    verify(userHandler).saveUser(any(UsersEntity.class));
    verify(clubsHandler).findByClubName(signUpDto.getClubName());
    verify(supportedClubsHandler).saveSupportedClub(any(SupportedClubsEntity.class));
  }

  @Test
  @DisplayName("회원가입 실패 : 잘못된 ProviderId")
  void failedGetSignUpDataByProviderId() {
    // given
    when(redisHandler.getSignUpDataByProviderId(signUpDto.getProviderId()))
        .thenThrow(new GlobalException(FailedResultType.PROVIDER_ID_NOT_FOUND));

    // when & then
    assertThrows(GlobalException.class, () -> authServiceImpl.signup(signUpDto));
  }
}