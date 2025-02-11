package com.service.sport_companion.api.controller;

import com.service.sport_companion.api.service.NewsService;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.type.NewsType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
public class NewsController {

  private final NewsService newsService;

  @GetMapping
  public ResponseEntity<ResultResponse> getNews(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(newsService.getNews(NewsType.TEXT, pageable));
  }

  @GetMapping("/video")
  public ResponseEntity<ResultResponse> getVideo(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(newsService.getNews(NewsType.VIDEO, pageable));
  }
}
