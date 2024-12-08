package com.service.sport_companion.api.component.club;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.SportsEntity;
import com.service.sport_companion.domain.repository.SportRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SportHandlerTest {

  @Mock
  private SportRepository sportRepository;

  @InjectMocks
  private SportHandler sportHandler;

  private final String SPORT_NAME = "야구";

  private SportsEntity sportsEntity;

  @BeforeEach
  void setUp() {
    sportsEntity = SportsEntity.builder()
        .sportId(1L)
        .sportName(SPORT_NAME)
        .build();
  }

  @Test
  @DisplayName("종목 조회 성공")
  void successReturnSportEntity() {
    // given
    when(sportRepository.findBySportName(SPORT_NAME)).thenReturn(Optional.ofNullable(sportsEntity));

    // when
    SportsEntity response = sportHandler.findBySportName(SPORT_NAME);

    // then
    assertEquals(sportsEntity, response);
  }

  @Test
  @DisplayName("종목 조회 실패")
  void failedReturnSportEntity() {
    // given
    when(sportRepository.findBySportName(SPORT_NAME)).thenReturn(Optional.empty());

    // when & then
    assertThrows(GlobalException.class, () -> sportHandler.findBySportName(SPORT_NAME));
  }
}