package com.service.sport_companion.api.component;

import com.service.sport_companion.api.auth.nickname.NicknameHandler;
import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.SignUpDataEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.auth.KakaoUserDetailsDTO;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.repository.CustomTopicRecommendRepository;
import com.service.sport_companion.domain.repository.CustomTopicRepository;
import com.service.sport_companion.domain.repository.SignUpDataRepository;
import com.service.sport_companion.domain.repository.SupportedClubsRepository;
import com.service.sport_companion.domain.repository.UserVoteRepository;
import com.service.sport_companion.domain.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHandler {

  private final UsersRepository usersRepository;
  private final NicknameHandler nicknameHandler;
  private final SignUpDataRepository signUpDataRepository;
  private final CustomTopicRecommendRepository customTopicRecommendRepository;
  private final CustomTopicRepository customTopicRepository;
  private final UserVoteRepository userVoteRepository;
  private final SupportedClubsRepository supportedClubsRepository;


  /**
   * 회원 가입 여부 검증 및 User 객체 반환 메서드
   */
  public UsersEntity findUserByUserInfo(KakaoUserDetailsDTO userInfo) {
    return usersRepository.findByEmail(userInfo.getEmail())
        .map(user -> {
          if (!user.getProvider().equals(userInfo.getProvider())) {
            throw new GlobalException(FailedResultType.EMAIL_ALREADY_USED);
          }
          return user;
        })
        .orElse(null);
  }


  /**
   * 랜덤 닉네임 검증 및 반환 메서드
   */
  public String getRandomNickname(int retryCount) {
    final int MAX_RETRY = 10;
    String nickname = nicknameHandler.getRandomNickname();

    if (usersRepository.existsByNickname(nickname)) {
      if (retryCount >= MAX_RETRY) {
        throw new GlobalException(FailedResultType.UNIQUE_NICKNAME_FAILED);
      }

      return getRandomNickname(retryCount + 1);
    }

    return nickname;
  }

  /**
   * userId와 일치하는 User 객체 반환
   */
  public UsersEntity findByUserId(long userId) {
    return usersRepository.findByUserId(userId)
        .orElseThrow(() -> new GlobalException(FailedResultType.USER_NOT_FOUND));
  }

  /**
   * nickname 존재 여부 확인 후 반환
   */
  public boolean existsByNickname(String nickname) {
    return usersRepository.existsByNickname(nickname);
  }


  /**
   * 사용자 정보 캐시에 저장
   */
  public void saveSingUpCacheData(KakaoUserDetailsDTO userInfo) {
    signUpDataRepository.save(SignUpDataEntity.builder()
        .providerId(userInfo.getProviderId())
        .email(userInfo.getEmail())
        .provider(userInfo.getProvider())
        .build());
  }

  /**
   * 사용자 정보 저장
   */
  public void saveUser(UsersEntity user) {
    usersRepository.save(user);
  }

  /**
   * 사용자 모든 데이터 삭제
   */
  @Transactional
  public void deleteAllUserData(UsersEntity user) {
    customTopicRecommendRepository.deleteAllByUsersEntity(user);
    customTopicRepository.deleteAllByUsers(user);
    userVoteRepository.deleteAllByUsersEntity(user);
    supportedClubsRepository.deleteAllByUser(user);

    usersRepository.delete(user);

  }
}
