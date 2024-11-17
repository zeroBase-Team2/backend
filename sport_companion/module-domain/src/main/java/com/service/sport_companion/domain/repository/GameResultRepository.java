package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.GameResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameResultRepository extends JpaRepository<GameResultEntity, Long> {

}
