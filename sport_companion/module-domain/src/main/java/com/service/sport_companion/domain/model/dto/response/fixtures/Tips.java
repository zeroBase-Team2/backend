package com.service.sport_companion.domain.model.dto.response.fixtures;

import com.service.sport_companion.domain.entity.TipsEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class Tips {

  private String SeatName;

  private String theme;

  private String SeatNum;

  public static List<Tips> of(List<TipsEntity> tipsEntities) {
    return tipsEntities.stream()
        .map(tips -> new Tips(
            tips.getSeatName(),
            tips.getTheme(),
            tips.getSeatNum()
        )).toList();
  }
}
