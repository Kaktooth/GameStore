package com.store.gamestore.common.data.loader;

public interface SparkFileLoader<T> {

  T loadTextFile(String path);

  T loadModel(String path);

  T loadDataset(String path);
}
