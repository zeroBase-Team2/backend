package com.service.sport_companion.api.component;

import com.service.sport_companion.domain.entity.CandidateEntity;
import com.service.sport_companion.domain.entity.VoteEntity;
import com.service.sport_companion.domain.model.dto.request.vote.CreateVoteDto;
import com.service.sport_companion.domain.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CandidateHandler {

  private final CandidateRepository candidateRepository;

  public void createCandidate(VoteEntity voteEntity, CreateVoteDto voteDto) {
    String[] examples = {
      voteDto.getExample1(),
      voteDto.getExample2(),
      voteDto.getExample3(),
      voteDto.getExample4(),
      voteDto.getExample5()
    };

    for (String example : examples) {
      candidateRepository.save(CandidateEntity.builder()
        .voteEntity(voteEntity)
        .example(example)
        .build());
    }
  }
}
