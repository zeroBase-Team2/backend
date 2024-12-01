package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.SportsEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends JpaRepository<SportsEntity, Long> {

  Optional<SportsEntity> findBySportName(String sportName);

}
