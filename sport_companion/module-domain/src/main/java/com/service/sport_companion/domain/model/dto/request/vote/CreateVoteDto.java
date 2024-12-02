package com.service.sport_companion.domain.model.dto.request.vote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
public class CreateVoteDto {

  private LocalDate startDate;

  private String topic;

  private String example1;

  private String example2;

  private String example3;

  private String example4;

  private String example5;

  @JsonCreator
  public CreateVoteDto(
    @JsonProperty("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
    @JsonProperty("topic")String topic,
    @JsonProperty("example1")String example1,
    @JsonProperty("example2")String example2,
    @JsonProperty("example3")String example3,
    @JsonProperty("example4")String example4,
    @JsonProperty("example5")String example5
  ) {
    this.startDate = startDate;
    this.topic = topic;
    this.example1 = example1;
    this.example2 = example2;
    this.example3 = example3;
    this.example4 = example4;
    this.example5 = example5;
  }
}
