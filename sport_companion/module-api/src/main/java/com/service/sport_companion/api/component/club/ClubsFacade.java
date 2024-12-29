package com.service.sport_companion.api.component.club;

import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.FixturesEntity;
import com.service.sport_companion.domain.entity.SupportedClubsEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.dto.response.clubs.Clubs;
import com.service.sport_companion.domain.model.dto.response.fixtures.FixtureDetails;
import com.service.sport_companion.domain.model.dto.response.fixtures.Fixtures;
import com.service.sport_companion.domain.model.dto.response.fixtures.Restaurant;
import com.service.sport_companion.domain.model.dto.response.fixtures.Tips;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.repository.ClubsRepository;
import com.service.sport_companion.domain.repository.FixturesRepository;
import com.service.sport_companion.domain.repository.RestaurantRepository;
import com.service.sport_companion.domain.repository.SupportedClubsRepository;
import com.service.sport_companion.domain.repository.TipsRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubsFacade {

  // 레포지토리 의존성
  private final ClubsRepository clubsRepository;
  private final SupportedClubsRepository supportedClubsRepository;
  private final RestaurantRepository restaurantRepository;
  private final TipsRepository tipsRepository;
  private final FixturesRepository fixturesRepository;

  // 구단 관련 메서드
  // -----------------------------------

  /**
   * 모든 구단 정보 조회
   */
  public List<Clubs> getAllClubList() {
    log.info("모든 구단 정보 조회.");

    List<Clubs> clubs = Clubs.of(clubsRepository.findAll());

    log.info("총 {}개의 구단 정보 조회 성공", clubs.size());
    return clubs;
  }

  /**
   * 구단명과 일치하는 ClubEntity 조회
   */
  public ClubsEntity findByClubName(String clubName) {
    log.info("구단명 '{}' 일치하는 구단 정보조회", clubName);

    return clubsRepository.findByClubName(clubName);
  }

  /**
   * 구단명에 특정 문자열이 포함된 ClubEntity 조회
   */
  public ClubsEntity findByFieldContaining(String clubName) {
    log.info("'{}' 포함된 구단 정보 조회.", clubName);

    return clubsRepository.findByClubNameContaining(clubName);
  }

  /**
   * 새로운 구단 저장
   */
  public void saveClub(ClubsEntity club) {
    log.info("{} 구단 저장", club.getClubName());

    clubsRepository.save(club);

    log.info("{} 구단 저장 성공", club.getClubName());
  }


  // 선호 구단 관련 메서드
  // -----------------------------------

  /**
   * 사용자 ID와 일치하는 선호 구단 정보 조회
   */
  public ClubsEntity getSupportClubsByUserId(Long userId) {
    log.info("사용자 ID '{}'에 해당하는 선호 구단 정보 조회", userId);

    return supportedClubsRepository.findByUserUserId(userId)
        .map(SupportedClubsEntity::getClub)
        .orElse(null);
  }

  /**
   * 선호 구단 저장
   */
  public void saveSupportedClub(UsersEntity user, String clubName) {
    log.info("사용자 '{}' : 선호 구단 : '{}' 저장", user.getUserId(), clubName);
    if (supportedClubsRepository.existsByUser(user)) {
      log.warn("이미 선호 구단을 등록하였습니다.");
      throw new GlobalException(FailedResultType.SUPPORT_CLUB_ALREADY_REGISTERED);
    }
    SupportedClubsEntity supportedClub = SupportedClubsEntity.builder()
        .user(user)
        .club(findByClubName(clubName))
        .build();

    supportedClubsRepository.save(supportedClub);
    log.info("선호 구단 : '{}' 등록 성공", clubName);
  }

  /**
   * 선호 구단 삭제
   */
  public void deleteSupportClubs(Long userId, String clubName) {
    log.info("사용자 ID : '{}'의 선호 구단 : '{}' 삭제.", userId, clubName);

    SupportedClubsEntity supportClubs = supportedClubsRepository
        .findByUserUserIdAndClubClubName(userId, clubName)
        .orElseThrow(() -> {
          log.warn("사용자 ID '{}'와 구단명 '{}'에 해당하는 선호 구단 정보를 찾을 수 없습니다.", userId, clubName);

          return new GlobalException(FailedResultType.SUPPORT_NOT_FOUND);
        });

    supportedClubsRepository.delete(supportClubs);
    log.info("사용자 ID '{}'의 선호 구단 '{}'이(가) 성공적으로 삭제되었습니다.", userId, clubName);
  }

  // 경기 일정 관련 메서드
  // -----------------------------------

  /**
   * 경기 일정 목록 조회
   */
  public List<Fixtures> getFixtureList(LocalDate fixtureDate, Long userId) {
    log.info("날짜 '{}'와 사용자 ID '{}'에 해당하는 경기 일정을 조회합니다.", fixtureDate, userId);

    // 사용자 ID가 null이면 모든 경기 일정 조회, 아니면 선호 구단 조회
    ClubsEntity clubs = (userId != null) ? getSupportClubsByUserId(userId) : null;

    // 조건에 따라 경기 일정 조회
    List<Fixtures> fixtures = (clubs == null) ?
        Fixtures.of(fixturesRepository.findAllByFixtureDate(fixtureDate)) :
        Fixtures.of(fixturesRepository.findClubFixtures(fixtureDate, clubs));

    // 로그 출력
    if (clubs == null) {
      log.info("모든 경기 일정 조회. 날짜 '{}'에 총 {}개의 경기 조회 성공", fixtureDate, fixtures.size());
    } else {
      log.info("날짜 '{}'에 선호 구단 '{}'의 경기 {}개 조회 성공", fixtureDate, clubs.getClubName(), fixtures.size());
    }

    return fixtures;
  }

  /**
   * 구단 별 경기 일정 조회
   */
  public List<Fixtures> getClubFixtureList(String clubName, LocalDate fixtureDate) {
    log.info("날짜 '{}'와 구단 '{}'에 해당하는 경기 일정을 조회합니다.", fixtureDate, clubName);

    ClubsEntity clubs = findByClubName(clubName);

    return Fixtures.of(fixturesRepository.findClubFixtures(fixtureDate, clubs));
  }

  /**
   * 경기 상세 정보 조회
   */
  public FixtureDetails getFixtureDetails(Long fixtureId) {
    log.info("경기 ID '{}'에 해당하는 상세 정보 조회.", fixtureId);

    FixturesEntity fixtures = findFixturesFixtureId(fixtureId);

    List<Restaurant> restaurants = Restaurant.of(restaurantRepository.findAllByClub(fixtures.getHomeClub()));
    List<Tips> tips = Tips.of(tipsRepository.findAllByClubs(fixtures.getHomeClub()));
    String siteUrl = resolveSiteUrl(fixtures);

    log.info("경기 ID '{}'의 상세 정보 조회 성공.", fixtureId);
    Fixtures fixture = Fixtures.of(fixtures);
    return new FixtureDetails(fixture, restaurants, tips, siteUrl);
  }

  /**
   * 크롤링한 경기 일정 저장
   */
  public void saveFixtureList(List<FixturesEntity> fixturesList) {
    log.info("총 {}개의 경기 일정 저장", fixturesList.size());

    fixturesRepository.saveAll(fixturesList);

    log.info("경기 일정 저장 성공");
  }

  /**
   * 특정 ID와 일치하는 경기 일정 조회
   */
  private FixturesEntity findFixturesFixtureId(Long fixtureId) {
    log.info("경기 ID '{}'에 해당하는 경기 일정 조회", fixtureId);

    return fixturesRepository.findById(fixtureId)
        .orElseThrow(() -> {
          log.warn("경기 ID '{}'에 해당하는 경기 일정을 찾을 수 없습니다.", fixtureId);
          return new GlobalException(FailedResultType.FIXTURE_NOT_FOUND);
        });
  }

  /**
   * 경기 예매처 사이트 URL 결정
   */
  private String resolveSiteUrl(FixturesEntity fixtures) {
    return fixtures.getFixtureDate().isBefore(LocalDate.now())
        ? fixtures.getHomeClub().getReservationSite()
        : null;
  }

}