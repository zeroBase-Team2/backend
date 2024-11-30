package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.ClubsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubsRepository extends JpaRepository<ClubsEntity, Long> {

  ClubsEntity findByClubName(String clubName);

  @Query("SELECT c FROM Clubs c WHERE c.clubName LIKE %:team%")
  ClubsEntity findByFieldContaining(@Param("team") String team);
}
