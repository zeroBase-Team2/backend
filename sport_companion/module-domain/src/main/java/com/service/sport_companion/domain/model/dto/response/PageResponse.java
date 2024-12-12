package com.service.sport_companion.domain.model.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageResponse<T> {

  private int pageNumber;
  private int totalPage;
  private Long totalData;
  private List<T> dataList;

}
