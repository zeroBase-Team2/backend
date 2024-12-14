package com.service.sport_companion.core.component;

import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.model.type.FailedResultType;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebDriverHandler {

  private final Environment environment;

  @Value("${selenium.server.url:default-for-dev}")
  private String SELENIUM_SERVER_URL;

  // 크롬 옵션 설정
  public ChromeOptions chromeOptions() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless"); // 헤드리스 모드 활성화

    return options;
  }

  public WebDriver getDriver() {
    if (environment.acceptsProfiles(Profiles.of("prod"))) {
      return webDriverChrome();
    } else {
      return webDriverSafari();
    }
  }

  // 배포 환경에서 WebDriver 생성해서 리턴
  private WebDriver webDriverChrome() {
    WebDriver driver;
    try {
      driver = new RemoteWebDriver(new URL(SELENIUM_SERVER_URL), chromeOptions());
    } catch (MalformedURLException e) {
      throw new GlobalException(FailedResultType.SELENIUM_SERVER_ERROR);
    }

    return driver;
  }

  // 개발 환경에서 WebDriver 생성해서 리턴
  private WebDriver webDriverSafari() {
    return new SafariDriver();
  }
}
