package com.service.sport_companion.batch.news;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.service.sport_companion.domain.entity.NewsEntity;
import com.service.sport_companion.domain.model.type.NewsType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MbcNewsCrawlingTest {

  @InjectMocks
  private MbcNewsCrawling mbcNewsCrawling;

  @Test
  @DisplayName("parsing : html에서 정확히 파싱하는지 테스트")
  void parsingSuccess() {
    // given
    String baseUrl = "https://imnews.imbc.com";
    String html = """
            <body>
              <div class="result_list">
                <ul>
                  <li>
                    <a href="href_url">
                      <span class="img ico_vod">
                        <img src="img_url">
                      </span>
                      <span class="txt_w">
                        <span class="tit">headline</span>
                        <span class="sub"><span class="date">""" + LocalDate.now() + """
                      </span></span>
                      </span>
                    </a>
                  </li>
                </ul>
              </div>
            </body>
      """;

    // when
    List<NewsEntity> result = mbcNewsCrawling.parsing(html);

    // then
    assertEquals(result.size(), 1);
    assertEquals(result.get(0).getHeadline(), "headline");
    assertEquals(result.get(0).getThumbnail(), "img_url");
    assertEquals(result.get(0).getNewsLink(), baseUrl + "href_url");
    assertEquals(result.get(0).getNewsDate(), LocalDate.now());
    assertEquals(result.get(0).getNewsType(), NewsType.VIDEO);
  }
}