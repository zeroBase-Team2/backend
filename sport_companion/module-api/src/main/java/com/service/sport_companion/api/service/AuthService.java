package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

  // 카카오 회원가입 or 로그인
  String oAuthForKakao(String code, HttpServletResponse response);

  // 닉네임 중복 확인
  ResultResponse checkNickname(String nickname);
}
