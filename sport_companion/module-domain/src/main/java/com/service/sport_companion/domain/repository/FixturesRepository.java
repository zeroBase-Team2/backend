package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.FixturesEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FixturesRepository extends JpaRepository<FixturesEntity, Long> {

  List<FixturesEntity> findAllByFixtureDate(LocalDate fixtureDate);

  @Query("SELECT f FROM Fixtures f "
      + "WHERE f.fixtureDate = :fixtureDate "
      + "AND (f.homeClub = :clubs OR f.awayClub = :clubs)")
  List<FixturesEntity> findClubFixtures(
      @Param("fixtureDate") LocalDate fixtureDate,
      @Param("clubs") ClubsEntity clubs
  );

}
