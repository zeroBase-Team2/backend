package com.service.sport_companion.domain.model.dto.response.vote;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.sport_companion.domain.entity.VoteEntity;
import com.service.sport_companion.domain.model.type.VoteResultType;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteResponse {

  private String resultType;

  private Long voteId;

  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/seoul")
  private LocalDate startDate;

  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/seoul")
  private LocalDate endDate;

  private String topic;

  private List<Vote> vote;

  private Long myVote;

  private Long totalVote;


  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  static class Vote {

    private Long candidateId;
    private String example;
    private Long voteCount;
  }

  // 투표 결과는 리턴하지 않는 생성자
  public static VoteResponse fromEntity(VoteEntity voteEntity) {
    List<Vote> voteList = voteEntity.getCandidateEntity().stream().map(
      candidate -> Vote.builder()
        .candidateId(candidate.getCandidateId())
        .example(candidate.getExample())
        .build()
    ).toList();

    return VoteResponse.builder()
      .resultType(VoteResultType.VOTE.name())
      .voteId(voteEntity.getVoteId())
      .startDate(voteEntity.getStartDate())
      .endDate(voteEntity.getEndDate())
      .topic(voteEntity.getTopic())
      .vote(voteList)
      .build();
  }

  // 투표 결과를 함께 리턴하는 생성자
  public static VoteResponse fromEntityWithResult(VoteEntity voteEntity,
    List<CandidateAndCountDto> candidateList,
    Long myVote
  ) {
    VoteResponse voteResponse = VoteResponse.builder()
      .resultType(VoteResultType.RESULT.name())
      .voteId(voteEntity.getVoteId())
      .startDate(voteEntity.getStartDate())
      .endDate(voteEntity.getEndDate())
      .topic(voteEntity.getTopic())
      .myVote(myVote)
      .totalVote(0L)
      .build();

    voteResponse.vote = candidateList.stream().map(
      candidate -> {
        // 전체 투표수의 합을 저장하기 위해 각 후보의 투표수를 더함
        voteResponse.totalVote += candidate.getVoteCount();

        return Vote.builder()
          .candidateId(candidate.getCandidateEntity().getCandidateId())
          .example(candidate.getCandidateEntity().getExample())
          .voteCount(candidate.getVoteCount())
          .build();
      }
    ).toList();

    return voteResponse;
  }
}
