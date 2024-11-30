package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.SeasonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonsRepository extends JpaRepository<SeasonsEntity, Long> {

  SeasonsEntity findBySeasonName(String seasonName);

}
