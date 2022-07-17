package com.chotot.framework.base;

import java.util.function.Supplier;

@FunctionalInterface
public interface ThrowableSupplier<R> extends Supplier<R> {
    default R get() {
        try {
            return getThrowable();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    R getThrowable() throws Throwable;
}
