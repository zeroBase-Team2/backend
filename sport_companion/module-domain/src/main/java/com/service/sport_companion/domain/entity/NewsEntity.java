package com.service.sport_companion.domain.entity;

import com.service.sport_companion.domain.model.type.NewsType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "News")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class NewsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long newsId;

  private LocalDate newsDate;

  private String newsLink;

  private String thumbnail;

  private String headline;

  @Enumerated(value = EnumType.STRING)
  private NewsType newsType;

  @CreatedDate
  private LocalDateTime createdAt;
}
