package com.service.sport_companion.domain.entity;

import com.service.sport_companion.domain.model.type.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UsersEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @NotNull
  private String email;

  private String nickname;

  private String provider;

  private String providerId;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  private LocalDateTime createdAt;
}
