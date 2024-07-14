package com.alura.literatura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IDataConverter {
  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public <T> T getData(String json, Class<T> class1) {
    try {
      return objectMapper.readValue(json, class1);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
