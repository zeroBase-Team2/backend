package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.SupportedClubsEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportedClubsRepository extends JpaRepository<SupportedClubsEntity, Long> {

  Optional<SupportedClubsEntity> findByUserUserId(Long userId);

}
