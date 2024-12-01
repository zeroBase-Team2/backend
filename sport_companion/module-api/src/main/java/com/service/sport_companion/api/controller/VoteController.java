package com.service.sport_companion.api.controller;

import com.service.sport_companion.api.service.VoteService;
import com.service.sport_companion.domain.model.annotation.CallUser;
import com.service.sport_companion.domain.model.dto.request.vote.CreateVoteDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vote")
@RequiredArgsConstructor
public class VoteController {

  private final VoteService voteService;

  @PostMapping
  public ResponseEntity<ResultResponse<Void>> createVote(@CallUser Long userId,
    @RequestBody CreateVoteDto voteDto
  ) {
    ResultResponse<Void> response = voteService.createVote(userId, voteDto);

    return new ResponseEntity<>(response, response.getStatus());
  }

  @PutMapping("/{voteId}")
  public ResponseEntity<ResultResponse<Void>> updateVote(@CallUser Long userId,
    @PathVariable("voteId") Long voteId, @RequestBody CreateVoteDto voteDto
  ) {
    ResultResponse<Void> response = voteService.updateVote(userId, voteId, voteDto);

    return new ResponseEntity<>(response, response.getStatus());
  }
}
