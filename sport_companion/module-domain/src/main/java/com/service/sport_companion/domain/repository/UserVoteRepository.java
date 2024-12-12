package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.UserVoteEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVoteRepository extends JpaRepository<UserVoteEntity, Long> {

  @Query("SELECT u FROM UserVoteEntity u "
    + "WHERE u.usersEntity.userId = :userId AND "
    + "FUNCTION('DATE', u.voteDateTime) = :date")
  Optional<UserVoteEntity> findTodayVote(
    @Param("userId") Long userId,
    @Param("date") LocalDate date
  );

  @Modifying
  @Query(value = "INSERT "
    + "INTO user_vote(user_id, candidate_id, vote_date_time) "
    + "VALUES(:userId, :candidateId, now())", nativeQuery = true)
  void vote(@Param("userId") Long userId, @Param("candidateId") Long candidateId);

  @Query("SELECT u "
    + "FROM UserVoteEntity u "
    + "WHERE u.usersEntity.userId = :userId "
    + "AND u.candidateEntity.candidateId IN :candidateId "
    + "ORDER BY u.voteDateTime DESC "
    + "LIMIT 1")
  Optional<UserVoteEntity> findMyRecentVoted(Long userId, List<Long> candidateId);


  void deleteAllByUsersEntity(UsersEntity user);
}
