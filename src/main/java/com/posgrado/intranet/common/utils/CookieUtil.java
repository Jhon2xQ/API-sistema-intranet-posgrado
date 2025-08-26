package com.posgrado.intranet.common.utils;

import org.springframework.stereotype.Component;

import com.posgrado.intranet.common.properties.CookieProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CookieUtil {
  private final CookieProperties cookieProperties;

  // Nombres de las cookies
  public static final String ACCESS_TOKEN_COOKIE = "accessToken";
  public static final String REFRESH_TOKEN_COOKIE = "refreshToken";
}
