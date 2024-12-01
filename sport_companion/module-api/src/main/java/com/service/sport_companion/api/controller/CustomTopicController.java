package com.service.sport_companion.api.controller;

import com.service.sport_companion.api.service.CustomTopicService;
import com.service.sport_companion.domain.model.annotation.CallUser;
import com.service.sport_companion.domain.model.dto.request.topic.CreateTopicDto;
import com.service.sport_companion.domain.model.dto.response.PageResponse;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.topic.CustomTopicResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic")
public class CustomTopicController {

  private final CustomTopicService customTopicService;

  @PostMapping
  public ResponseEntity<ResultResponse<Void>> createTopic(@CallUser Long userId,
    @RequestBody CreateTopicDto createTopicDto
  ) {
    ResultResponse<Void> response = customTopicService.createTopic(userId, createTopicDto);

    return new ResponseEntity<>(response, response.getStatus());
  }

  @DeleteMapping("/{topicId}")
  public ResponseEntity<ResultResponse<Void>> deleteTopic(@CallUser Long userId,
    @PathVariable("topicId") Long topicId
  ) {
    ResultResponse<Void> response = customTopicService.deleteTopic(userId, topicId);

    return new ResponseEntity<>(response, response.getStatus());
  }

  // 주제 최신순으로 조회
  @GetMapping
  public ResponseEntity<ResultResponse<PageResponse<CustomTopicResponse>>> getTopicList(
    @CallUser Long userId,
    Pageable pageable
  ) {
    ResultResponse<PageResponse<CustomTopicResponse>> response = customTopicService
      .getTopicList(userId, pageable);

    return new ResponseEntity<>(response, response.getStatus());
  }

  // 주제 top5 조회
  @GetMapping("/top5")
  public ResponseEntity<ResultResponse<List<CustomTopicResponse>>> getTopicTop5(
    @CallUser Long userId
  ) {
    ResultResponse<List<CustomTopicResponse>> response = customTopicService.getTopicTop5(userId);

    return new ResponseEntity<>(response, response.getStatus());
  }
}
