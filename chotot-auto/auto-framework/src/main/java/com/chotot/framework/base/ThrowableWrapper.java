package com.chotot.framework.base;

public class ThrowableWrapper {

  public static <T> T wrapper(ThrowableSupplier<T> supplier) {
    return supplier.get();
  }
}
