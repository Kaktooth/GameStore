package com.store.gamestore.recommendator;

import org.apache.spark.ml.Model;

public interface TrainedModel<T extends Model<T>> {

  Model<T> getTrainedModel();

  void trainModel();
}
