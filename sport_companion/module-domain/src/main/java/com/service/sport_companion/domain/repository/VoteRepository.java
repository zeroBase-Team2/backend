package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.VoteEntity;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {

  boolean existsByStartDate(LocalDate startDate);
}
