package com.service.sport_companion.api.controller;

import com.service.sport_companion.api.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  @GetMapping("/kakao")
  public ResponseEntity<Void> oAuthForKakao(@RequestParam String code, HttpServletResponse response) {

    log.info("Kakao code {}", code);
    String redirectUrl = authService.oAuthForKakao(code, response);

    log.info("Kakao redirect {}", redirectUrl);
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create(redirectUrl));

    return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
  }

}
