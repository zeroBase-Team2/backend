package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.UserVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVoteRepository extends JpaRepository<UserVoteEntity, Long> {

}
