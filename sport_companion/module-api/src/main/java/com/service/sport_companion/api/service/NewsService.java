package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.response.PageResponse;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.news.NewsResponseDto;
import com.service.sport_companion.domain.model.type.NewsType;
import org.springframework.data.domain.Pageable;

public interface NewsService {

  ResultResponse<PageResponse<NewsResponseDto>> getNews(NewsType newsType, Pageable pageable);
}
