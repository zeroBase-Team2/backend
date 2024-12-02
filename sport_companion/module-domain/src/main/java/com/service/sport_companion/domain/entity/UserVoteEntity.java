package com.service.sport_companion.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "user_vote")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserVoteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userVoteId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UsersEntity usersEntity;

  @ManyToOne
  @JoinColumn(name = "candidate_id", nullable = false)
  private CandidateEntity candidateEntity;

  @CreatedDate
  private LocalDateTime voteDateTime;
}
