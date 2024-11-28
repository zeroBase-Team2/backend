package com.service.sport_companion.api.service.impl;

import com.service.sport_companion.api.component.NewsHandler;
import com.service.sport_companion.api.service.NewsService;
import com.service.sport_companion.domain.entity.NewsEntity;
import com.service.sport_companion.domain.model.dto.request.news.NewsParameterDto;
import com.service.sport_companion.domain.model.dto.response.PageResponse;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.news.NewsResponseDto;
import com.service.sport_companion.domain.model.type.NewsType;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImpl implements NewsService {

  private final NewsHandler newsHandler;

  @Override
  public ResultResponse getTodayNews(
    NewsParameterDto newsParameter, NewsType newsType, Pageable pageable
  ) {
    Page<NewsEntity> newsPage = newsHandler.findNewsByNewsDateOrderByRecent(
      newsParameter, newsType, pageable);

    return new ResultResponse(SuccessResultType.SUCCESS_GET_NEWS_LIST,
      new PageResponse<>(
        newsPage.getNumber(),
        newsPage.getTotalPages(),
        newsPage.getTotalElements(),
        newsPage.getContent().stream()
          .map(NewsResponseDto::from)
          .toList()));
  }
}
