package com.service.sport_companion.api.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.service.sport_companion.api.component.CustomTopicHandler;
import com.service.sport_companion.api.component.UserHandler;
import com.service.sport_companion.core.exception.GlobalException;
import com.service.sport_companion.domain.entity.CustomTopicEntity;
import com.service.sport_companion.domain.entity.UsersEntity;
import com.service.sport_companion.domain.model.dto.response.ResultResponse;
import com.service.sport_companion.domain.model.type.SuccessResultType;
import com.service.sport_companion.domain.model.type.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class CustomTopicServiceImplTest {

  @InjectMocks
  private CustomTopicServiceImpl customTopicService;

  @Mock
  private CustomTopicHandler customTopicHandler;

  @Mock
  private UserHandler userHandler;

  @Captor
  private ArgumentCaptor<Pageable> pageableCaptor;

  private final long AUTHOR_ID = 1L;
  private final long TOPIC_ID = 2L;
  private UsersEntity author;
  private CustomTopicEntity topic;

  @BeforeEach
  public void setup() {
    author = UsersEntity.builder()
      .userId(AUTHOR_ID)
      .role(UserRole.USER)
      .build();

    topic = CustomTopicEntity.builder()
      .users(author)
      .build();
  }

  @Test
  @DisplayName("자신이 등록한 Topic 삭제_성공")
  public void deleteMyTopic() {
    // given
    given(userHandler.findByUserId(AUTHOR_ID)).willReturn(author);
    given(customTopicHandler.findById(TOPIC_ID)).willReturn(topic);

    // when
    ResultResponse<Void> response = customTopicService.deleteTopic(AUTHOR_ID, TOPIC_ID);

    // then
    assertEquals(response.getMessage(), SuccessResultType.SUCCESS_DELETE_CUSTOM_TOPIC.getMessage());
    assertEquals(response.getStatus(), HttpStatus.OK);
    assertNull(response.getData());
  }

  @Test
  @DisplayName("관리자 Topic 삭제_성공")
  public void deleteTopicByAdmin() {
    // given
    long adminId = 2L;
    UsersEntity admin = UsersEntity.builder()
      .userId(adminId)
      .role(UserRole.ADMIN)
      .build();

    given(userHandler.findByUserId(adminId)).willReturn(admin);
    given(customTopicHandler.findById(TOPIC_ID)).willReturn(topic);

    // when
    ResultResponse<Void> response = customTopicService.deleteTopic(adminId, TOPIC_ID);

    // then
    assertEquals(response.getMessage(), SuccessResultType.SUCCESS_DELETE_CUSTOM_TOPIC.getMessage());
    assertEquals(response.getStatus(), HttpStatus.OK);
    assertNull(response.getData());
  }

  @Test
  @DisplayName("다른 사람 토픽 삭제_실패")
  public void deleteTopicByOther() {
    // given
    long otherUserId = 3L;
    UsersEntity otherUser = UsersEntity.builder()
      .userId(otherUserId)
      .role(UserRole.USER)
      .build();

    given(userHandler.findByUserId(otherUserId)).willReturn(otherUser);
    given(customTopicHandler.findById(TOPIC_ID)).willReturn(topic);

    // when & then
    assertThrows(GlobalException.class, () -> customTopicService.deleteTopic(otherUserId, TOPIC_ID));
  }

  @Test
  @DisplayName("페이지 정보와 함께 토픽 조회_성공")
  public void getTopicWithPageable() {
    // given
    Pageable pageable = Pageable.ofSize(1);
    given(customTopicHandler.findTopicOrderByCreatedAt(pageable)).willReturn(Page.empty());

    // when
    customTopicService.getTopicList(AUTHOR_ID, pageable);

    verify(customTopicHandler).findTopicOrderByCreatedAt(pageableCaptor.capture());
    Pageable capturedPageable = pageableCaptor.getValue();

    // then
    assertTrue(capturedPageable.isPaged());
  }

  @Test
  @DisplayName("페이지 정보 없이 토픽 조회_성공")
  public void getTopicWithoutPageable() {
    // given
    Pageable pageable = Pageable.unpaged();
    given(customTopicHandler.findTopicOrderByCreatedAt(pageable)).willReturn(Page.empty());

    // when
    customTopicService.getTopicList(AUTHOR_ID, null);

    verify(customTopicHandler).findTopicOrderByCreatedAt(pageableCaptor.capture());
    Pageable capturedPageable = pageableCaptor.getValue();

    // then
    assertTrue(capturedPageable.isUnpaged());
  }
}