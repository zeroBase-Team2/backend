package com.service.sport_companion.domain.model.dto.response.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.sport_companion.domain.entity.NewsEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseDto {

  private String headline;
  private String newsLink;
  private String thumbnail;
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/seoul")
  private LocalDateTime newsDateTime;

  public static NewsResponseDto from(NewsEntity newsEntity) {
    return NewsResponseDto.builder()
      .headline(newsEntity.getHeadline())
      .newsLink(newsEntity.getNewsLink())
      .thumbnail(newsEntity.getThumbnail())
      .newsDateTime(newsEntity.getCreatedAt())
      .build();
  }
}
