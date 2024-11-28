package com.service.sport_companion.domain.model.dto.request.news;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class NewsParameterDto {

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

  public NewsParameterDto() {
    this.date = LocalDate.now();
  }
}
