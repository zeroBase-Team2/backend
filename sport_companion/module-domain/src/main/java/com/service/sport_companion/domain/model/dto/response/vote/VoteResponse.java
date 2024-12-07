package com.service.sport_companion.domain.model.dto.response.vote;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.sport_companion.domain.entity.CandidateEntity;
import com.service.sport_companion.domain.entity.VoteEntity;
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

  private Long voteId;

  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/seoul")
  private LocalDate startDate;

  private String topic;

  private List<Vote> vote;

  private Long myVote;


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
  public static VoteResponse fromExample(VoteEntity voteEntity,
    List<CandidateEntity> candidateList
  ) {
    List<Vote> voteList = candidateList.stream().map(
      candidate -> Vote.builder()
        .candidateId(candidate.getCandidateId())
        .example(candidate.getExample())
        .build()
    ).toList();

    return VoteResponse.builder()
      .voteId(voteEntity.getVoteId())
      .startDate(voteEntity.getStartDate())
      .topic(voteEntity.getTopic())
      .vote(voteList)
      .build();
  }

  // 투표 결과를 함께 리턴하는 생성자
  public static VoteResponse fromExampleAndVoteCount(VoteEntity voteEntity,
    List<CandidateAndCountDto> candidateList, Long myVote
  ) {
    List<Vote> voteList = candidateList.stream().map(
      candidate -> Vote.builder()
        .candidateId(candidate.getCandidateEntity().getCandidateId())
        .example(candidate.getCandidateEntity().getExample())
        .voteCount(candidate.getVoteCount())
        .build()
    ).toList();

    return VoteResponse.builder()
      .voteId(voteEntity.getVoteId())
      .startDate(voteEntity.getStartDate())
      .topic(voteEntity.getTopic())
      .vote(voteList)
      .myVote(myVote)
      .build();
  }
}
