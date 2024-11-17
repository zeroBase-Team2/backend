package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.PlayersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayersRepository extends JpaRepository<PlayersEntity, Long> {

}
