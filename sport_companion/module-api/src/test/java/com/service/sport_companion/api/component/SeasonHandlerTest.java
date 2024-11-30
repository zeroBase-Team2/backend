package com.service.sport_companion.api.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.service.sport_companion.domain.entity.SeasonsEntity;
import com.service.sport_companion.domain.repository.SeasonsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SeasonHandlerTest {

  @Mock
  private SeasonsRepository seasonsRepository;

  @InjectMocks
  private SeasonHandler seasonHandler;

  private final String SEASON_NAME = "KBO 정규시즌 일정";

  private SeasonsEntity seasons;

  @BeforeEach
  void setUp() {
    seasons = SeasonsEntity.builder()
        .seasonId(1L)
        .seasonName(SEASON_NAME).build();

  }

  @Test
  @DisplayName("시즌 정보 조회 성공")
  void getSeasonsBySeasonName() {
    // given
    when(seasonsRepository.findBySeasonName(SEASON_NAME)).thenReturn(seasons);

    // when
    SeasonsEntity response = seasonHandler.findBySeasonName(SEASON_NAME);

    // then
    assertEquals(seasons, response);
  }

}