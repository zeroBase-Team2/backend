package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.user.UserInfo;

public interface UsersService {

  ResultResponse<Void> updateUserInfo(Long userId, String nickname);

  ResultResponse<UserInfo> getUserInfo(Long userId);
}
