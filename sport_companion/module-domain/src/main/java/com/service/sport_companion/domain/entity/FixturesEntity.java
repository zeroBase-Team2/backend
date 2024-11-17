package com.service.sport_companion.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Fixtures")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FixturesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long fixtureId;

  @ManyToOne
  @JoinColumn(name = "club_id", nullable = false)
  private ClubsEntity homeClub;

  @ManyToOne
  @JoinColumn(name = "club_id", nullable = false)
  private ClubsEntity awayClub;

  private LocalDate fixtureDate;

  private LocalTime fixtureTime;

  private String stadium;
}
