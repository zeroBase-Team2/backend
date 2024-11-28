package com.service.sport_companion.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.service.sport_companion.api.service.NewsService;
import com.service.sport_companion.domain.model.dto.request.news.NewsParameterDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class NewsControllerTest {

  private MockMvc mockMvc;

  @Mock
  private NewsService newsService;

  @InjectMocks
  private NewsController newsController;

  private final String baseUrl = "/api/v1/news";

  @Captor
  private ArgumentCaptor<NewsParameterDto> newsParameterCaptor;

  @Captor
  private ArgumentCaptor<Pageable> pageableCaptor;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders
      .standaloneSetup(newsController)
      .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
      .build();
  }

  @Test
  @DisplayName("파라미터 테스트 : 입력값이 있을 때")
  public void parameterHasSizeAndDate() throws Exception {
    // given
    int size = 5;
    LocalDate date = LocalDate.of(2024, 12, 12);

    // when
    mockMvc.perform(get(baseUrl)
        .param("size", String.valueOf(size))
        .param("date", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
      )
      .andExpect(status().isOk());

    // then
    verify(newsService).getTodayNews(newsParameterCaptor.capture(), pageableCaptor.capture());

    NewsParameterDto capturedDto = newsParameterCaptor.getValue();
    Pageable capturedPageable = pageableCaptor.getValue();

    assertEquals(capturedDto.getDate(), date);
    assertEquals(capturedPageable.getPageSize(), size);
  }

  @Test
  @DisplayName("파라미터 테스트 : 입력값이 없을 때")
  public void parameterNoInput() throws Exception {
    // given
    int defaultSize = 10;
    mockMvc.perform(get(baseUrl)).andExpect(status().isOk());

    // when
    verify(newsService).getTodayNews(newsParameterCaptor.capture(), pageableCaptor.capture());

    // then
    NewsParameterDto capturedDto = newsParameterCaptor.getValue();
    Pageable capturedPageable = pageableCaptor.getValue();

    assertEquals(capturedDto.getDate(), LocalDate.now());
    assertEquals(capturedPageable.getPageSize(), defaultSize);
  }
}