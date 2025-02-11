package com.service.sport_companion.domain.model.dto.request.club;


import com.service.sport_companion.domain.entity.ClubsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClubDto {
  private String name;

  private String stadium;

  private String stadiumAddress;

  private String reservationSite;

  private MultipartFile file;

  public static ClubsEntity of(ClubDto clubDto, String emblemImg) {
    return ClubsEntity.builder()
        .clubName(clubDto.getName())
        .clubStadium(clubDto.getStadium())
        .stadiumAddress(clubDto.getStadiumAddress())
        .reservationSite(clubDto.getReservationSite())
        .emblemImg(emblemImg)
        .build();
  }
}