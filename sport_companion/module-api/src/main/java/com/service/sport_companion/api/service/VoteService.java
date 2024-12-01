package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.request.vote.CreateVoteDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;

public interface VoteService {

  ResultResponse<Void> createVote(Long userId, CreateVoteDto voteDto);
}
