package com.service.sport_companion.api.auth.jwt;

import com.service.sport_companion.domain.model.annotation.CallUser;
import com.service.sport_companion.domain.model.type.TokenType;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CallUserResolver implements HandlerMethodArgumentResolver {

  private final JwtUtil jwtUtil;

  // CallUser 어노테이션이 붙은 메서드 파라미터를 대상으로 resolver 수행
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterAnnotation(CallUser.class) != null;
  }

  // request로부터 토큰을 추출하여 userId를 반환
  @Override
  public Long resolveArgument(@Nullable MethodParameter parameter,
    ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
    WebDataBinderFactory binderFactory) {

    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

    String tokenHeader = request.getHeader(TokenType.ACCESS.getValue());

    if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
      return null;
    }

    String accessToken = tokenHeader.substring(7);

    return jwtUtil.getUserId(accessToken);
  }
}
