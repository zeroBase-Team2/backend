package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.UsersEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

  Optional<UsersEntity> findByUserId(long userId);

  Optional<UsersEntity> findByEmail(String email);

  boolean existsByNickname(String email);
}
