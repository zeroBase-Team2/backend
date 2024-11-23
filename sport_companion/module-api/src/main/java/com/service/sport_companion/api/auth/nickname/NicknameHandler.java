package com.service.sport_companion.api.auth.nickname;

import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.model.type.UrlType;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class NicknameHandler {

  public String getRandomNickname() {

    // HTTP 헤더 설정
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/x-www-form-urlencoded");
    headers.add("Cache-Control", "no-cache");

    // 요청 데이터 생성
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("lang", "ko");

    // HTTP 요청 생성
    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

    // RestTemplate 인스턴스 생성
    RestTemplate restTemplate = new RestTemplate();

    try {
      // POST 요청 전송
      ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
          UrlType.RANDOM_NICKNAME_URL.getUrl(),
          HttpMethod.POST,
          requestEntity,
          new ParameterizedTypeReference<>() {}
      );

      // 응답 성공 시 닉네임 추출
      if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
        Map<String, Object> responseBody = response.getBody();
        return responseBody.get("data").toString();
      } else {
        throw new GlobalException(FailedResultType.UNIQUE_NICKNAME_SERVER_ERROR);
      }
    } catch (Exception e) {
      // 네트워크 오류 처리
      throw new GlobalException(FailedResultType.UNIQUE_NICKNAME_SERVER_ERROR);
    }
  }
}