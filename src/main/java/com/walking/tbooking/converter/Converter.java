package com.walking.tbooking.converter;

public interface Converter<F, T> {
    T convert(F from);
}
