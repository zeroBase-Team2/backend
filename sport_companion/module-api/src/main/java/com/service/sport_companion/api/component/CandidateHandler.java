package com.service.sport_companion.api.component;

import com.service.sport_companion.domain.entity.CandidateEntity;
import com.service.sport_companion.domain.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CandidateHandler {

  private final CandidateRepository candidateRepository;

  public void createCandidate(CandidateEntity candidateEntity) {
    candidateRepository.save(candidateEntity);
  }
}
