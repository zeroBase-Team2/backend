package com.service.sport_companion.batch.news;

import com.service.sport_companion.batch.component.WebDriverHandler;
import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.NewsEntity;
import com.service.sport_companion.domain.model.type.FailedResultType;
import com.service.sport_companion.domain.model.type.NewsType;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MbcNewsCrawling {

  private final WebDriverHandler webDriverHandler;

  // 배치로 실행시킬 MBC News 크롤링 메인 작업
  @Transactional
  public List<NewsEntity> crawlingMain() {
    String url = "https://imnews.imbc.com/news/2024/sports/";
    WebDriver driver = webDriverHandler.getWebDriver(url);

    try {
      return parsing(driver.getPageSource());
    } finally {
      driver.quit();
    }
  }

  // pageSource에서 파싱하여 필요한 뉴스 데이터 가져오기
  public List<NewsEntity> parsing(String pageSource) {
    Document doc = Jsoup.parse(pageSource);
    Elements listArea = doc.getElementsByClass("list_area");
    // 가장 오래된 데이터부터 가져오기 위해 List reverse
    Collections.reverse(listArea);

    List<NewsEntity> newsItemList = new ArrayList<>();
    for (Element list : listArea) {
      Elements items = list.select("ul > li");
      Collections.reverse(items);

      for (Element item : items) {
        String newsLink = item.select("a").attr("href");
        String thumbnail = item.select("a > .img > img").attr("src");
        String headline = item.select("a > .txt_w > .tit").text();
        NewsType newsType = (item.select("a > .img").hasClass("ico_vod")) ?
          NewsType.VIDEO : NewsType.TEXT;

        if (newsLink.isEmpty() || thumbnail.isEmpty() || headline.isEmpty()) {
          throw new GlobalException(FailedResultType.MBC_NEWS_PARSING_FAILED);
        }

        newsItemList.add(NewsEntity.builder()
          .newsLink(newsLink)
          .thumbnail(thumbnail)
          .headline(headline)
          .newsType(newsType)
          .newsDate(LocalDate.now())
          .build());
      }
    }
    return newsItemList;
  }
}
