package com.service.sport_companion.api.component.club;

import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.model.dto.response.clubs.Clubs;
import com.service.sport_companion.domain.repository.ClubsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClubsHandler {

  private final ClubsRepository clubsRepository;

  /**
   * 구단명과 일치하는 ClubEntity 조회
   */
  public ClubsEntity findByClubName(String clubName) {
    return clubsRepository.findByClubName(clubName);
  }

  /**
   * 모든 구단 정보 조회
   */
  public List<Clubs> getAllClubList() {
    return clubsRepository.findAll().stream()
        .map(clubsEntity -> new Clubs(clubsEntity.getClubName(), clubsEntity.getEmblemImg()))
        .toList();
  }

  /**
   * 매개변수의 구단명이 포함 되어 있는 ClubEntity 조회
   */
  public ClubsEntity findByFieldContaining(String clubName) {
    return clubsRepository.findByClubNameContaining(clubName);
  }

  /**
   * 등록한 구단 저장
   */
  public void saveClub(ClubsEntity clubs) {
    clubsRepository.save(clubs);
  }
}
