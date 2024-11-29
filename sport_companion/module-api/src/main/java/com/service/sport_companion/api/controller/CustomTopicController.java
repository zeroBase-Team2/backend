package com.service.sport_companion.api.controller;

import com.service.sport_companion.api.service.CustomTopicService;
import com.service.sport_companion.domain.model.annotation.CallUser;
import com.service.sport_companion.domain.model.dto.request.topic.CreateTopicDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
