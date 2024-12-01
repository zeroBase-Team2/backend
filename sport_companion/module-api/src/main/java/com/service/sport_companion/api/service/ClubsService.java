package com.service.sport_companion.api.service;

import com.service.sport_companion.domain.model.dto.request.club.ClubDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.clubs.Clubs;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ClubsService {

  ResultResponse<List<Clubs>> getAllClubList();

  ResultResponse<Void> addClub(ClubDto clubDto) throws IOException;
}
