package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.CandidateEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {

  List<CandidateEntity> findByVoteEntity_VoteIdOrderBySequence(Long voteId);
}
