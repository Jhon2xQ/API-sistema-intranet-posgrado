package com.posgrado.intranet.dtos;

import lombok.Data;

@Data
public class ApiResponse<T> {
  private boolean success;
  private String message;
  private T data;
  private Long timestamp;
  
  public ApiResponse(boolean success, String message, T data) {
      this.success = success;
      this.message = message;
      this.data = data;
      this.timestamp = System.currentTimeMillis();
  }
  
  public static <T> ApiResponse<T> success(String message, T data) {
      return new ApiResponse<>(true, message, data);
  }
  
  public static <T> ApiResponse<T> success(String message) {
      return new ApiResponse<>(true, message, null);
  }
  
  public static <T> ApiResponse<T> error(String message) {
      return new ApiResponse<>(false, message, null);
  }
}
