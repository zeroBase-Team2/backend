package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.request.news.NewsParameterDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import org.springframework.data.domain.Pageable;

public interface NewsService {

  ResultResponse getTodayNews(NewsParameterDto newsParameter, Pageable pageable);
}
