package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.FixturesEntity;
import com.service.sport_companion.domain.entity.SeasonsEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FixturesRepository extends JpaRepository<FixturesEntity, Long> {

  List<FixturesEntity> findAllByFixtureDateAndSeasons(LocalDate fixtureDate,
      SeasonsEntity seasons);

  @Query("SELECT f FROM Fixtures f "
      + "WHERE f.fixtureDate = :fixtureDate "
      + "AND f.seasons = :seasons "
      + "AND (f.homeClub = :clubs OR f.awayClub = :clubs)")
  List<FixturesEntity> findSupportFixtures(@Param("fixtureDate") LocalDate fixtureDate,
      @Param("seasons") SeasonsEntity seasons,
      @Param("clubs") ClubsEntity clubs);


}
