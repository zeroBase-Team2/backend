package com.service.sport_companion.api.controller;

import com.service.sport_companion.api.service.ClubsService;
import com.service.sport_companion.domain.model.dto.request.club.ClubDto;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.dto.response.clubs.Clubs;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clubs")
public class ClubsController {

  private final ClubsService clubsService;

  // 모든 구단 정보 조회
  @GetMapping("/all")
  public ResponseEntity<ResultResponse<List<Clubs>>> getAllClubs() {

    ResultResponse<List<Clubs>> response = clubsService.getAllClubList();
    return new ResponseEntity<>(response, response.getStatus());
  }

  // 구단 등록
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ResultResponse<Void>> addClub(
      @RequestParam("name") String name,
      @RequestParam("stadium") String stadium,
      @RequestParam("stadiumAddress") String stadiumAddress,
      @RequestParam("reservationSite") String reservationSite,
      @RequestPart("file") MultipartFile file) throws IOException{

    ClubDto clubDto = new ClubDto(name, stadium, stadiumAddress, reservationSite, file);

    ResultResponse<Void> response = clubsService.addClub(clubDto);
    return new ResponseEntity<>(response, response.getStatus());
  }

}
