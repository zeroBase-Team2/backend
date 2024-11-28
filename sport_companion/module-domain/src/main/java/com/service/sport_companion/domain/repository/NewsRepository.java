package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.NewsEntity;
import com.service.sport_companion.domain.model.type.NewsType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

  boolean existsByNewsLink(String newsLink);

  List<NewsEntity> findByNewsDateAndNewsTypeOrderByCreatedAt(LocalDate newsDate, NewsType newsType, Pageable pageable);
}
