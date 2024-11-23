package com.service.sport_companion.api.service.impl;


import com.service.sport_companion.api.component.UserHandler;
import com.service.sport_companion.domain.entity.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

  private final UserHandler userHandler;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    UsersEntity user = userHandler.findByUserId(Long.parseLong(userId));

    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .password("") // 비밀번호가 필요 없으므로 빈 값 설정
        .roles(user.getRole().toString()) // 사용자의 역할 설정
        .build();
  }
}
