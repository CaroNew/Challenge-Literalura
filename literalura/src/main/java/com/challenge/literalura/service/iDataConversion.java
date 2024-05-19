package com.challenge.literalura.service;

public interface iDataConversion {

    <T> T convertData(String data, Class<T> classType);
}
