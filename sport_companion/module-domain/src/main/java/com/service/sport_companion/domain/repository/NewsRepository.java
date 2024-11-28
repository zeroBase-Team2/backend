package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.NewsEntity;
import com.service.sport_companion.domain.model.type.NewsType;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

  boolean existsByNewsLink(String newsLink);

  Page<NewsEntity> findByNewsDateAndNewsTypeOrderByCreatedAt(LocalDate newsDate, NewsType newsType, Pageable pageable);
}
