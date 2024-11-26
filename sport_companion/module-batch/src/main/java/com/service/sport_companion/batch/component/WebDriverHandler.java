package com.service.sport_companion.batch.component;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebDriverHandler {

  @Value("${web.driver.id}")
  private String WEB_DRIVER_ID;

  @Value("${web.driver.path}")
  private String WEB_DRIVER_PATH;

  public ChromeOptions chromeOptions() {
    // 크롬 드라이버 경로 설정
    System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

    // 크롬 옵션 설정
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless"); // 헤드리스 모드 활성화

    return options;
  }

  // url로 WebDriver 생성해서 리턴
  public WebDriver getWebDriver(String url) {
    WebDriver driver = new ChromeDriver(chromeOptions());
    driver.get(url);

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(
      d -> ((JavascriptExecutor) d).executeScript("return jQuery.active == 0"));

    return driver;
  }
}
