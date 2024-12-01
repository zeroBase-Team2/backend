package com.service.sport_companion.api.component;

import com.service.sport_companion.domain.entity.VoteEntity;
import com.service.sport_companion.domain.model.dto.request.vote.CreateVoteDto;
import com.service.sport_companion.domain.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteHandler {

  private final VoteRepository voteRepository;

  public VoteEntity createVote(CreateVoteDto voteDto) {
    return voteRepository.save(VoteEntity.builder()
      .topic(voteDto.getTopic())
      .startDate(voteDto.getStartDate())
      .build());
  }
}
