package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.SportsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends JpaRepository<SportsEntity, Long> {

}
