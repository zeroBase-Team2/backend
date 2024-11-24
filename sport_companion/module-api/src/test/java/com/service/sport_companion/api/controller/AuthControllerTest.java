package com.service.sport_companion.api.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.service.sport_companion.api.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class AuthControllerTest {

  private MockMvc mockMvc;

  @Mock
  private AuthService authService;

  @InjectMocks
  private AuthController authController;

  private final String baseUrl = "/api/v1/auth";

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
  }

  @Test
  @DisplayName("checkNickname : 사용 가능한 닉네임 확인")
  void checkNickname_AvailableNickname() throws Exception {
    // given
    String nickname = "validNickname";
    boolean isValidNickname = true;

    given(authService.checkNickname(nickname)).willReturn(isValidNickname);

    // when & then
    mockMvc.perform(get(baseUrl + "/check-nickname/{nickname}", nickname))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.message").value("사용 가능한 닉네임입니다."))
      .andExpect(jsonPath("$.data").value(isValidNickname))
      .andDo(print());
  }

  @Test
  @DisplayName("checkNickname : 사용 불가능한(중복) 닉네임 확인")
  void checkNickname_UnavailableNickname() throws Exception {
    // given
    String nickname = "invalidNickname";
    boolean isValidNickname = false;

    given(authService.checkNickname(nickname)).willReturn(isValidNickname);

    // when & then
    mockMvc.perform(get(baseUrl + "/check-nickname/{nickname}", nickname))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.message").value("이미 사용 중인 닉네임입니다."))
      .andExpect(jsonPath("$.data").value(isValidNickname))
      .andDo(print());
  }

  @Test
  @DisplayName("checkNickname : 사용 불가능한(빈 값) 닉네임 확인")
  void checkNickname_BlankNickname() throws Exception {
    // when & then
    mockMvc.perform(get(baseUrl + "/check-nickname/{nickname}", " "))
      .andExpect(status().isBadRequest())
      .andDo(print());
  }
}