package com.posgrado.intranet.common.utils;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.posgrado.intranet.common.properties.CookieProperties;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CookieUtil {
  private final CookieProperties cookieProperties;
    
  // Nombres de las cookies
  public final String ACCESS_TOKEN_COOKIE = "accessToken";
  public final String REFRESH_TOKEN_COOKIE = "refreshToken";
  
  /**
   * Crear cookie para el access token
   */
  public void createAccessTokenCookie(HttpServletResponse response, String token) {
    Cookie cookie = createCookie(ACCESS_TOKEN_COOKIE, token, cookieProperties.getMaxAge());
    response.addCookie(cookie);
    log.debug("Cookie de access token creada");
  }
  
  /**
   * Crear cookie para el refresh token
   */
  public void createRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
    Cookie cookie = createCookie(REFRESH_TOKEN_COOKIE, refreshToken, cookieProperties.getRefreshMaxAge());
    response.addCookie(cookie);
    log.debug("Cookie de refresh token creada");
  }
  
  /**
   * Obtener token desde cookie
   */
  public Optional<String> getTokenFromCookie(HttpServletRequest request, String cookieName) {
    if (request.getCookies() == null) {
        return Optional.empty();
    }
    
    return Arrays.stream(request.getCookies())
        .filter(cookie -> cookieName.equals(cookie.getName()))
        .map(Cookie::getValue)
        .findFirst();
  }
  
  /**
   * Eliminar cookies (logout)
   */
  public void clearTokenCookies(HttpServletResponse response) {
    clearCookie(response, ACCESS_TOKEN_COOKIE);
    clearCookie(response, REFRESH_TOKEN_COOKIE);
    log.debug("Cookies de tokens eliminadas");
  }
  
  /**
   * Crear cookie base con configuración de seguridad
   */
  private Cookie createCookie(String name, String value, int maxAge) {
    Cookie cookie = new Cookie(name, value);
    cookie.setHttpOnly(true); // Previene acceso desde JavaScript (XSS)
    cookie.setSecure(cookieProperties.isSecure()); // Solo HTTPS en producción
    cookie.setPath(cookieProperties.getPath());
    cookie.setMaxAge(maxAge);
    
    // SameSite attribute (manual porque Cookie no lo soporta nativamente)
    // Se maneja en el response con header adicional
    
    if (cookieProperties.getDomain() != null && !cookieProperties.getDomain().isEmpty()) {
        cookie.setDomain(cookieProperties.getDomain());
    }
    
    return cookie;
  }
  
  /**
   * Eliminar cookie específica
   */
  private void clearCookie(HttpServletResponse response, String cookieName) {
    Cookie cookie = new Cookie(cookieName, "");
    cookie.setHttpOnly(true);
    cookie.setSecure(cookieProperties.isSecure());
    cookie.setPath(cookieProperties.getPath());
    cookie.setMaxAge(0); // Expira inmediatamente
    
    if (cookieProperties.getDomain() != null) {
        cookie.setDomain(cookieProperties.getDomain());
    }
    
    response.addCookie(cookie);
  }
  
  /**
   * Verificar si existe cookie de access token
   */
  public boolean hasAccessToken(HttpServletRequest request) {
    return getTokenFromCookie(request, ACCESS_TOKEN_COOKIE).isPresent();
  }
  
  /**
   * Verificar si existe cookie de refresh token
   */
  public boolean hasRefreshToken(HttpServletRequest request) {
    return getTokenFromCookie(request, REFRESH_TOKEN_COOKIE).isPresent();
  }
}
