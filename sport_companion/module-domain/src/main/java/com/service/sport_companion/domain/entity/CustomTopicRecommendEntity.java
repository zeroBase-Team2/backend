package com.service.sport_companion.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "custom_topic_recommend")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomTopicRecommendEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long customTopicRecommendId;

  @ManyToOne
  @JoinColumn(name = "custom_topic_id", nullable = false)
  private CustomTopicEntity customTopic;

  @ManyToOne
  @JoinColumn(name = "users_id", nullable = false)
  private UsersEntity usersEntity;
}
