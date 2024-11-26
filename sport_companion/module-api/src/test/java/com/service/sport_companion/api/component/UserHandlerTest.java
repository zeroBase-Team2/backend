package com.service.sport_companion.api.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.service.sport_companion.api.auth.nickname.NicknameHandler;
import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.SignUpDataEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.auth.KakaoUserDetailsDTO;
import com.service.sport_companion.domain.model.type.UserRole;
import com.service.sport_companion.domain.repository.SignUpDataRepository;
import com.service.sport_companion.domain.repository.UsersRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

  @Mock
  private UsersRepository usersRepository;

  @Mock
  private NicknameHandler nicknameHandler;

  @Mock
  private SignUpDataRepository signUpDataRepository;

  @InjectMocks
  private UserHandler userHandler;

  private static final String EMAIL = "test@email.com";
  private static final String NICKNAME = "nickname";
  private static final String KAKAO_PROVIDER = "kakao";
  private static final String KAKAO_PROVIDER_ID = "kakao_provider_id";
  private static final String NAVER_PROVIDER = "naver_provider";
  private static final int MAX_RETRY = 10;
  private static final Long USERID = 1L;

  private KakaoUserDetailsDTO kakaoUserDetails;
  private UsersEntity user;
  private UsersEntity mockUser;
  private SignUpDataEntity signUpDataEntity;

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

    mockUser = Mockito.mock(UsersEntity.class);

    signUpDataEntity = SignUpDataEntity.builder()
        .providerId(KAKAO_PROVIDER_ID)
        .email(EMAIL)
        .provider(KAKAO_PROVIDER)
        .build();
  }


  @Test
  @DisplayName("findUserByUserInfo : 새로운 사용자")
  void shouldReturnNullForNewUser() {
    // given
    when(usersRepository.findByEmail(kakaoUserDetails.getEmail())).thenReturn(Optional.empty());

    // when
    UsersEntity response = userHandler.findUserByUserInfo(kakaoUserDetails);

    // then
    assertNull(response);
  }

  @Test
  @DisplayName("findUserByUserInfo : 기존 회원")
  void shouldReturnUserUserExists() {
    // given
    when(usersRepository.findByEmail(kakaoUserDetails.getEmail())).thenReturn(Optional.of(mockUser));
    when(mockUser.getProvider()).thenReturn(KAKAO_PROVIDER);

    // when
    UsersEntity response = userHandler.findUserByUserInfo(kakaoUserDetails);

    // then
    assertEquals(response.getUserId(), mockUser.getUserId());
    assertEquals(response.getEmail(), mockUser.getEmail());
    assertEquals(response.getNickname(), mockUser.getNickname());
  }

  @Test
  @DisplayName("findUserByUserInfo : 다른 소셜 사이트로 가입 한 경우")
  void shouldThrowExceptionEmailAlreadyExists() {
    // given
    when(usersRepository.findByEmail(kakaoUserDetails.getEmail())).thenReturn(Optional.of(mockUser));
    when(mockUser.getProvider()).thenReturn(NAVER_PROVIDER);

    // when & then
    assertThrows(GlobalException.class, () -> userHandler.findUserByUserInfo(kakaoUserDetails));
  }


  @Test
  @DisplayName("getRandomNickname : 랜덤 닉네임 반환 성공")
  void shouldReturnRandomNickname() {
    // given
    when(nicknameHandler.getRandomNickname()).thenReturn(NICKNAME);
    when(usersRepository.existsByNickname(NICKNAME)).thenReturn(false);

    // when
    String response = userHandler.getRandomNickname(1);

    // then
    assertEquals(response, NICKNAME);
  }

  @Test
  @DisplayName("getRandomNickname : 랜덤 닉네임 반환 실패")
  void shouldReturnExceptionUniqueNickname() {
    // given
    when(nicknameHandler.getRandomNickname()).thenReturn(NICKNAME);
    when(usersRepository.existsByNickname(NICKNAME)).thenReturn(true);

    // when & then
    assertThrows(GlobalException.class, () -> userHandler.getRandomNickname(1));
    verify(nicknameHandler, times(MAX_RETRY)).getRandomNickname();
    verify(usersRepository, times(MAX_RETRY)).existsByNickname(NICKNAME);
  }

  @Test
  @DisplayName("findByUserId : 사용자 반환 성공")
  void shouldReturnUserFindUser() {
    // given
    when(usersRepository.findByUserId(USERID)).thenReturn(Optional.of(user));

    // when
    UsersEntity response = userHandler.findByUserId(USERID);

    // then
    assertEquals(response.getUserId(), user.getUserId());
    assertEquals(response.getEmail(), user.getEmail());
    assertEquals(response.getNickname(), user.getNickname());
  }

  @Test
  @DisplayName("findByUserId : 사용자 반환 실패")
  void shouldReturnExceptionUserNotFound() {
    // given
    when(usersRepository.findByUserId(USERID)).thenReturn(Optional.empty());

    // when & then
    assertThrows(GlobalException.class, () -> userHandler.findByUserId(USERID));
  }

  @Test
  @DisplayName("회원가입 데이터를 Redis에 저장 성공")
  void saveSingUpCacheDataSuccessfully() {
    // given
    when(signUpDataRepository.save(signUpDataEntity)).thenReturn(signUpDataEntity);

    // when
    SignUpDataEntity response = signUpDataRepository.save(signUpDataEntity);

    // then
    assertEquals(response, signUpDataEntity);
  }

  @Test
  @DisplayName("회원 정보 저장 성공")
  void saveUserSuccessfully() {
    // given
    when(usersRepository.save(user)).thenReturn(user);

    // when
    UsersEntity response = usersRepository.save(user);

    // then
    assertEquals(response, user);
  }
}