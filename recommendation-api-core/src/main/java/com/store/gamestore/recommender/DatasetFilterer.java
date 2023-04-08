package com.store.gamestore.recommender;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface DatasetFilterer extends Filterer<Dataset<Row>> {

}
