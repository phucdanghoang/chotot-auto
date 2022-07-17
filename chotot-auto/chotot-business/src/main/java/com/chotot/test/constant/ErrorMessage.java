package com.chotot.test.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
  Err001("Key: 'AuthLoginRequest.password' Error:Field validation for 'password' failed on the 'min' tag"),
  Err002("Không tìm thấy thông tin người dùng.");

  private final String message;
}
