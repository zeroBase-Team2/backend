package com.service.sport_companion.api.component;

import com.service.sport_companion.domain.entity.CandidateEntity;
import com.service.sport_companion.domain.repository.CandidateRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CandidateHandler {

  private final CandidateRepository candidateRepository;

  public void createCandidate(CandidateEntity candidateEntity) {
    candidateRepository.save(candidateEntity);
  }

  public List<CandidateEntity> getCandidateByVoteId(Long voteId) {
    return candidateRepository.findByVoteEntity_VoteIdOrderBySequence(voteId);
  }
}
