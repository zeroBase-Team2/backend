package com.service.sport_companion.batch.news;

import com.service.sport_companion.domain.entity.NewsEntity;
import com.service.sport_companion.domain.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NewsItemWriter implements ItemWriter<NewsEntity> {

  private final NewsRepository newsRepository;

  @Override
  public void write(Chunk<? extends NewsEntity> chunk) {
    for (NewsEntity newsEntity : chunk) {
      if (!newsRepository.existsByNewsLink(newsEntity.getNewsLink())) {
        newsRepository.save(newsEntity);
      }
    }
  }
}
