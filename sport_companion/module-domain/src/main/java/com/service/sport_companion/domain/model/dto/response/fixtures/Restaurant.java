package com.service.sport_companion.domain.model.dto.response.fixtures;

import com.service.sport_companion.domain.entity.RestaurantEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Restaurant {

  private String restaurantName;

  private String restaurantAddress;

  private Double lat;

  private Double lnt;

  public static List<Restaurant> of(List<RestaurantEntity> restaurants) {
    return restaurants.stream()
        .map(restaurantEntity -> new Restaurant(
            restaurantEntity.getRestaurantName(),
            restaurantEntity.getRestaurantAddress(),
            restaurantEntity.getLat(),
            restaurantEntity.getLnt()
        )).toList();
  }
}
