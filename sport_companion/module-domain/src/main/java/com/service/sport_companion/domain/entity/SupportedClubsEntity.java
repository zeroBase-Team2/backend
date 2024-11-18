package com.service.sport_companion.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "SupportedClubs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SupportedClubsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long supportedClubId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UsersEntity user;

  @ManyToOne
  @JoinColumn(name = "club_id", nullable = false)
  private ClubsEntity club;
}
