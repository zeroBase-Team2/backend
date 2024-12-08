package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.user.UserInfo;

public interface UsersService {

  // 사용자 정보 조회
  ResultResponse<UserInfo> getUserInfo(Long userId);

  // 사용자 정보 수정
  ResultResponse<Void> updateUserInfo(Long userId, String nickname);
}
