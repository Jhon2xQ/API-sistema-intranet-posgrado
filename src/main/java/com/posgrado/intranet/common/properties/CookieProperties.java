package com.posgrado.intranet.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "app.cookie")
@Data
public class CookieProperties {
  private String domain;
  private boolean secure;
  private String sameSite;
  private int accessMaxAge;
  private int refreshMaxAge;
  private String path;
}
