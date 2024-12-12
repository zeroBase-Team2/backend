package com.service.sport_companion.domain.model.dto.response.clubs;

import com.service.sport_companion.domain.entity.ClubsEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Clubs {

  String clubName;
  String emblemImg;

  public static List<Clubs> of(List<ClubsEntity> clubs) {
    return clubs.stream()
        .map(clubsEntity -> new Clubs(
            clubsEntity.getClubName(),
            clubsEntity.getEmblemImg()))
        .toList();
  }
}
