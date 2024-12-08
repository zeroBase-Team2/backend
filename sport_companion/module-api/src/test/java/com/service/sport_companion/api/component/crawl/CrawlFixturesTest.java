package com.service.sport_companion.api.component.crawl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.service.sport_companion.api.component.club.ClubsHandler;
import com.service.sport_companion.api.component.club.FixtureHandler;
import com.service.sport_companion.api.component.club.SeasonHandler;
import com.service.sport_companion.domain.entity.ClubsEntity;
import com.service.sport_companion.domain.entity.FixturesEntity;
import com.service.sport_companion.domain.entity.SeasonsEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CrawlFixturesTest {

  @Mock
  private SeasonHandler seasonHandler;

  @Mock
  private ClubsHandler clubsHandler;

  @Mock
  private FixtureHandler fixtureHandler;

  @InjectMocks
  private CrawlFixtures crawlFixtures;

  private SeasonsEntity mockedSeason;
  private ClubsEntity mockedHomeClub;
  private ClubsEntity mockedAwayClub;
  private FixturesEntity fixtures;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Mocked data setup
    mockedSeason = SeasonsEntity.builder()
        .seasonId(1L)
        .seasonName("KBO 정규시즌 일정")
        .build();

    mockedHomeClub = ClubsEntity.builder()
        .clubId(1L)
        .clubName("KT")
        .build();

    mockedAwayClub = ClubsEntity.builder()
        .clubId(2L)
        .clubName("두산")
        .build();
  }

  @Test
  void testParseToFixturesEntityWithSampleHtml() {
    // Given
    String year = "2024";
    String lastDate = "10.02(수)";
    String seriesKey = "KBO 정규시즌 일정";

    String sampleHtml = """
        <table>
          <tbody>
            <tr>
              <td class="day" rowspan="1">10.02(수)</td>
              <td class="time"><b>18:30</b></td>
              <td class="play">
                <span>KT</span>
                <em>
                  <span class="win">4</span>
                  <span>vs</span>
                  <span class="lose">0</span>
                </em>
                <span>두산</span>
              </td>
              <td class="relay">
                <a>리뷰</a>
              </td>
              <td>
                <a>
              </td>
              <td>K-2T</td>
              <td></td>
              <td>잠실</td>
              <td>-</td>
            </tr>
          </tbody>
        </table>
    """;

    Document doc = Jsoup.parse(sampleHtml);
    Element schedule = doc.selectFirst("tr");

    assertNotNull(schedule, "Parsed schedule is null. Check the provided HTML structure.");

    // Mock dependencies
    when(seasonHandler.findBySeasonName(seriesKey)).thenReturn(mockedSeason);
    when(clubsHandler.findByFieldContaining("KT")).thenReturn(mockedHomeClub);
    when(clubsHandler.findByFieldContaining("두산")).thenReturn(mockedAwayClub);

    // When
    FixturesEntity fixtures = crawlFixtures.parseToFixturesEntity(year, lastDate, schedule, seriesKey);

    // Then
    assertNotNull(fixtures, "Parsed fixtures is null.");
    assertEquals(LocalDate.of(2024, 10, 2), fixtures.getFixtureDate());
    assertEquals(LocalTime.parse("18:30"), fixtures.getFixtureTime());
    assertEquals(mockedHomeClub, fixtures.getHomeClub());
    assertEquals(mockedAwayClub, fixtures.getAwayClub());
    assertEquals(Integer.parseInt("4"), fixtures.getHomeScore());
    assertEquals(Integer.parseInt("0"), fixtures.getAwayScore());
    assertEquals("잠실", fixtures.getStadium());
    assertEquals("-", fixtures.getNotes());
  }
}