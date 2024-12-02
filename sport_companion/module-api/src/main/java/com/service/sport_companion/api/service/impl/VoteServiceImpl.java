package com.service.sport_companion.api.service.impl;

import com.service.sport_companion.api.component.CandidateHandler;
import com.service.sport_companion.api.component.VoteHandler;
import com.service.sport_companion.api.service.VoteService;
import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.CandidateEntity;
import com.service.sport_companion.domain.entity.VoteEntity;
import com.service.sport_companion.domain.model.dto.request.vote.CreateVoteDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteServiceImpl implements VoteService {

  private final VoteHandler voteHandler;
  private final CandidateHandler candidateHandler;

  @Override
  public ResultResponse<Void> createVote(Long userId, CreateVoteDto voteDto) {
    // 동일한 날짜에 이미 등록돼있으면 추가로 등록할 수 없음
    if (voteHandler.existByStartDate(voteDto.getStartDate())) {
      throw new GlobalException(FailedResultType.ALREADY_EXISTS_VOTE_DATE);
    }

    // 투표 주제 저장
    VoteEntity voteEntity = voteHandler.createVote(voteDto);

    // 투표 후보 5개 저장
    String[] examples = {
      voteDto.getExample1(),
      voteDto.getExample2(),
      voteDto.getExample3(),
      voteDto.getExample4(),
      voteDto.getExample5()
    };

    for (int i = 0; i < examples.length; i++) {
      candidateHandler.createCandidate(CandidateEntity.builder()
          .voteEntity(voteEntity)
          .example(examples[i])
          .sequence(i)
          .build());
    }

    return ResultResponse.of(SuccessResultType.SUCCESS_CREATE_VOTE);
  }

  @Override
  public ResultResponse<Void> updateVote(Long userId, Long voteId, CreateVoteDto voteDto) {
    // 투표 주제 업데이트
    VoteEntity voteEntity = voteHandler.getVoteEntity(voteId);
    voteEntity.update(voteDto);

    // 저장된 투표 후보를 가져와 각각 순서에 맞게 업데이트
    List<CandidateEntity> candidateEntityList = candidateHandler.getCandidateByVoteId(voteId);

    String[] examples = {
      voteDto.getExample1(),
      voteDto.getExample2(),
      voteDto.getExample3(),
      voteDto.getExample4(),
      voteDto.getExample5()
    };

    for (int i = 0; i < examples.length; i++) {
      candidateEntityList.get(i).updateExample(examples[i]);
    }

    return ResultResponse.of(SuccessResultType.SUCCESS_MODIFY_VOTE);
  }

  @Override
  public ResultResponse<Void> deleteVote(Long userId, Long voteId) {
    candidateHandler.deleteVoteByVoteId(voteId);
    voteHandler.deleteVoteById(voteId);

    return ResultResponse.of(SuccessResultType.SUCCESS_DELETE_VOTE);
  }
}
