package com.service.sport_companion.domain.model.dto.request.club;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClubDto {

  private String sportsName;

  private String clubName;

  private String clubStadium;

  private String siteName;

  private MultipartFile file;
}