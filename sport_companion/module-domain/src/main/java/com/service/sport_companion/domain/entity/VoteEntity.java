package com.service.sport_companion.domain.entity;

import com.service.sport_companion.domain.model.dto.request.vote.CreateVoteDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vote")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VoteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long voteId;

  private LocalDate startDate;

  private String topic;

  public void update(CreateVoteDto voteDto) {
    this.startDate = voteDto.getStartDate();
    this.topic = voteDto.getTopic();
  }
}
