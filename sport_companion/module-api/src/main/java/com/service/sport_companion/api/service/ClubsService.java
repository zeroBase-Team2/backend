package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.clubs.Clubs;
import java.util.List;

public interface ClubsService {

  ResultResponse<List<Clubs>> getAllClubList();
}
