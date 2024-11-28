package com.service.sport_companion.api.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.service.sport_companion.api.component.NewsHandler;
import com.service.sport_companion.domain.model.dto.request.news.NewsParameterDto;
import com.service.sport_companion.domain.model.dto.response.PageResponse;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.type.NewsType;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

  @InjectMocks
  private NewsServiceImpl newsService;

  @Mock
  private NewsHandler newsHandler;

  @Test
  @DisplayName("뉴스 조회 성공")
  public void getNewsSuccess() {
    // given
    given(newsHandler.findNewsByNewsDateOrderByRecent(
      any(NewsParameterDto.class), any(NewsType.class), any(Pageable.class))
    ).willReturn(Page.empty());

    // when
    ResultResponse response = newsService.getTodayNews(new NewsParameterDto(), NewsType.TEXT, Pageable.ofSize(5));

    // then
    assertInstanceOf(PageResponse.class, response.getData());
    assertEquals(response.getStatus(), HttpStatus.OK);
    assertEquals(response.getMessage(), SuccessResultType.SUCCESS_GET_NEWS_LIST.getMessage());
  }
}