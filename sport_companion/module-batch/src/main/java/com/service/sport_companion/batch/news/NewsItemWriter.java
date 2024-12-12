package com.service.sport_companion.batch.news;

import com.service.sport_companion.domain.entity.NewsEntity;
import com.service.sport_companion.domain.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class NewsItemWriter implements ItemWriter<NewsEntity> {

  private final NewsRepository newsRepository;

  @Override
  public void write(Chunk<? extends NewsEntity> chunk) {
    for (NewsEntity newsEntity : chunk) {
      if (!newsRepository.existsByNewsLink(newsEntity.getNewsLink())) {
        log.info("뉴스 DB 저장: {}", newsEntity.getHeadline());

        newsRepository.save(newsEntity);
      }
    }
  }
}
