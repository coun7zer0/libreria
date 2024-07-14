package com.alura.literatura.service;

public interface IDataConverter {
  <T> T getData(String json, Class<T> class1);
}
