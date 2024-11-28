package com.service.sport_companion.api.component;

import com.service.sport_companion.domain.entity.NewsEntity;
import com.service.sport_companion.domain.model.dto.request.news.NewsParameterDto;
import com.service.sport_companion.domain.model.type.NewsType;
import com.service.sport_companion.domain.repository.NewsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsHandler {

  private final NewsRepository newsRepository;

  public List<NewsEntity> findTextNewsByNewsDateOrderByRecent(
    NewsParameterDto newsParameter, Pageable pageable
  ) {
    return newsRepository.findByNewsDateAndNewsTypeOrderByCreatedAt(
      newsParameter.getDate(), NewsType.TEXT, pageable);
  }
}
