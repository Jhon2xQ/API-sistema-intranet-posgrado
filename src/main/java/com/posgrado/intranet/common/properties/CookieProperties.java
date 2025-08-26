package com.posgrado.intranet.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "app.cookie")
@Data
public class CookieProperties {
  private String domain = "localhost";
  private boolean secure = false; // true para HTTPS
  private String sameSite = "Strict"; // Strict, Lax, None
  private int maxAge = 86400; // 24 horas
  private int refreshMaxAge = 604800; // 7 días
  private String path = "/";
}
