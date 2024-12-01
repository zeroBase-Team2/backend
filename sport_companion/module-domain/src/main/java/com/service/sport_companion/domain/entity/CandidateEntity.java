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
@Table(name = "candidate")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long candidateId;

  @ManyToOne
  @JoinColumn(name = "vote_id", nullable = false)
  private VoteEntity voteEntity;

  private String example;

  private int sequence;

  public void updateExample(String example) {
    this.example = example;
  }
}
