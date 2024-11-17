package com.service.sport_companion.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "GameResult")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GameResultEntity {

  @Id
  private Long fixtureId;

  private int homeScore;

  private int awayScore;
}
