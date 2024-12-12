package com.service.sport_companion.api.component;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.service.sport_companion.api.component.club.ClubsFacade;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.FixturesEntity;
import com.service.sport_companion.domain.entity.RestaurantEntity;
import com.service.sport_companion.domain.entity.SupportedClubsEntity;
import com.service.sport_companion.domain.entity.TipsEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.dto.response.clubs.Clubs;
import com.service.sport_companion.domain.model.dto.response.fixtures.FixtureDetails;
import com.service.sport_companion.domain.model.dto.response.fixtures.Fixtures;
import com.service.sport_companion.domain.model.dto.response.fixtures.Restaurant;
import com.service.sport_companion.domain.model.dto.response.fixtures.Tips;
import com.service.sport_companion.domain.repository.ClubsRepository;
import com.service.sport_companion.domain.repository.FixturesRepository;
import com.service.sport_companion.domain.repository.RestaurantRepository;
import com.service.sport_companion.domain.repository.SupportedClubsRepository;
import com.service.sport_companion.domain.repository.TipsRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClubsFacadeTest {

  @Mock
  private ClubsRepository clubsRepository;

  @Mock
  private SupportedClubsRepository supportedClubsRepository;

  @Mock
  private RestaurantRepository restaurantRepository;

  @Mock
  private TipsRepository tipsRepository;

  @Mock
  private FixturesRepository fixturesRepository;

  @InjectMocks
  private ClubsFacade clubsFacade;

  private UsersEntity user;
  private ClubsEntity club1;
  private ClubsEntity club2;
  private SupportedClubsEntity supportedClub;
  private FixturesEntity fixtures;
  private List<RestaurantEntity> restaurant;
  private List<TipsEntity> tip;
  private FixtureDetails fixtureDetails;

  @BeforeEach
  void setUp() {
    user = UsersEntity.builder()
        .userId(1L)
        .nickname("TestUser")
        .build();

    club1 = ClubsEntity.builder()
        .clubId(1L)
        .clubName("KIA 타이거즈")
        .reservationSite("url~~")
        .build();

    club2 = ClubsEntity.builder()
        .clubId(1L)
        .clubName("KIA 타이거즈")
        .reservationSite("url~~")
        .build();

    supportedClub = SupportedClubsEntity.builder()
        .user(user)
        .club(club1)
        .build();

    fixtures = FixturesEntity.builder()
        .fixtureId(1L)
        .fixtureDate(LocalDate.now())
        .homeClub(club1)
        .awayClub(club2)
        .build();

    restaurant = List.of(
        RestaurantEntity.builder()
            .club(club1)
            .restaurantId(1L)
            .restaurantName("Restaurant A")
            .restaurantAddress("123 Main St")
            .lat(37.7749)
            .lnt(-122.4194)
            .build(),
        RestaurantEntity.builder()
            .club(club1)
            .restaurantId(2L)
            .restaurantName("Restaurant B")
            .restaurantAddress("456 Elm St")
            .lat(40.7128)
            .lnt(-74.0060)
            .build()
    );

    tip = List.of(
        TipsEntity.builder()
            .tipId(1L)
            .clubs(club1)
            .SeatName("A1")
            .theme("VIP")
            .SeatNum("101")
            .build(),
        TipsEntity.builder()
            .tipId(2L)
            .clubs(club1)
            .SeatName("B2")
            .theme("Standard")
            .SeatNum("202")
            .build()
    );


    fixtureDetails = new FixtureDetails(
        Restaurant.of(restaurant),
        Tips.of(tip),
        club1.getReservationSite()
    );
  }

  // 구단 관련 메서드 테스트
  @Test
  @DisplayName("getAllClubList: 모든 구단 정보 조회 성공")
  void shouldReturnAllClubList() {
    // given
    when(clubsRepository.findAll()).thenReturn(List.of(club1, club2));

    // when
    List<Clubs> result = clubsFacade.getAllClubList();

    // then
    assertEquals(2, result.size());
  }

  @Test
  @DisplayName("findByClubName: 구단명으로 구단 조회 성공")
  void shouldReturnClubByName() {
    // given
    when(clubsRepository.findByClubName(club1.getClubName())).thenReturn(club1);

    // when
    ClubsEntity result = clubsFacade.findByClubName(club1.getClubName());

    // then
    assertEquals(club1, result);
  }

  @Test
  @DisplayName("findByFieldContaining: 특정 문자열 포함 구단 조회 성공")
  void shouldReturnClubByContainingField() {
    // given
    when(clubsRepository.findByClubNameContaining("KIA")).thenReturn(club1);

    // when
    ClubsEntity result = clubsFacade.findByFieldContaining("KIA");

    // then
    assertEquals(club1, result);
  }

  @Test
  @DisplayName("saveClub: 새로운 구단 저장 성공")
  void shouldSaveClub() {
    // given
    when(clubsRepository.save(club1)).thenReturn(club1);

    // when
    assertDoesNotThrow(() -> clubsFacade.saveClub(club1));

    // then
    verify(clubsRepository, times(1)).save(club1);
  }

  // 선호 구단 관련 메서드 테스트
  @Test
  @DisplayName("getSupportClubsByUserId: 사용자 ID로 선호 구단 조회 성공")
  void shouldReturnSupportClubByUserId() {
    // given
    when(supportedClubsRepository.findByUserUserId(user.getUserId()))
        .thenReturn(Optional.of(supportedClub));

    // when
    ClubsEntity result = clubsFacade.getSupportClubsByUserId(user.getUserId());

    // then
    assertEquals(club1, result);
  }

  // 선호 구단 관련 메서드 테스트
  @Test
  @DisplayName("getSupportClubsByUserId: 사용자 ID로 선호 구단 조회 실패")
  void shouldReturnNullByUserId() {
    // given
    when(supportedClubsRepository.findByUserUserId(user.getUserId()))
        .thenReturn(Optional.empty());

    // when
    ClubsEntity result = clubsFacade.getSupportClubsByUserId(user.getUserId());

    // then
    assertNull(result);
  }

  @Test
  @DisplayName("saveSupportedClub: 선호 구단 저장 성공")
  void shouldSaveSupportedClub() {
    // given
    when(supportedClubsRepository.existsByUser(user)).thenReturn(false);
    when(clubsRepository.findByClubName(club1.getClubName())).thenReturn(club1);

    // when
    assertDoesNotThrow(() -> clubsFacade.saveSupportedClub(user, club1.getClubName()));

    // then
    verify(supportedClubsRepository, times(1)).save(any(SupportedClubsEntity.class));
  }

  @Test
  @DisplayName("deleteSupportClubs: 선호 구단 삭제 성공")
  void shouldDeleteSupportedClub() {
    // given
    when(supportedClubsRepository.findByUserUserIdAndClubClubName(user.getUserId(), club1.getClubName()))
        .thenReturn(Optional.of(supportedClub));

    // when
    assertDoesNotThrow(() -> clubsFacade.deleteSupportClubs(user.getUserId(), club1.getClubName()));

    // then
    verify(supportedClubsRepository, times(1)).delete(supportedClub);
  }

  // 경기 일정 관련 메서드 테스트
  @Test
  @DisplayName("getFixtureList: 모든 경기 일정 조회 성공")
  void shouldReturnAllFixtures() {
    // given
    when(fixturesRepository.findAllByFixtureDate(LocalDate.now()))
        .thenReturn(List.of(fixtures));

    // when
    List<Fixtures> result = clubsFacade.getFixtureList(LocalDate.now(), null);

    // then
    assertEquals(1, result.size());
    assertEquals(fixtures.getHomeClub().getClubName(), result.getFirst().getHomeClubName());
  }

  // 경기 일정 관련 메서드 테스트
  @Test
  @DisplayName("getFixtureList: 선호구단 경기 일정 조회 성공")
  void shouldReturnSupportClubFixtures() {
    // given
    when(fixturesRepository.findAllByFixtureDate(LocalDate.now()))
        .thenReturn(List.of(fixtures));

    // when
    List<Fixtures> result = clubsFacade.getFixtureList(LocalDate.now(), user.getUserId());

    // then
    assertEquals(1, result.size());
    assertEquals(fixtures.getHomeClub().getClubName(), result.getFirst().getHomeClubName());
  }

  @Test
  @DisplayName("getFixtureDetails: 경기 상세 정보 조회 성공")
  void shouldReturnFixtureDetails() {
    // given
    when(fixturesRepository.findById(fixtures.getFixtureId())).thenReturn(Optional.of(fixtures));
    when(restaurantRepository.findAllByClub(fixtures.getHomeClub())).thenReturn(restaurant);
    when(tipsRepository.findAllByClubs(fixtures.getHomeClub())).thenReturn(tip);

    // when
    FixtureDetails result = clubsFacade.getFixtureDetails(fixtures.getFixtureId());

    // then
    assertEquals(fixtureDetails.getRestaurants().size(), result.getRestaurants().size());
    assertEquals(fixtureDetails.getTips().size(), result.getTips().size());
  }

  @Test
  @DisplayName("saveFixtureList: 경기 일정 저장 성공")
  void shouldSaveFixtureList() {
    // when
    assertDoesNotThrow(() -> clubsFacade.saveFixtureList(List.of(fixtures)));

    // then
    verify(fixturesRepository, times(1)).saveAll(List.of(fixtures));
  }
}