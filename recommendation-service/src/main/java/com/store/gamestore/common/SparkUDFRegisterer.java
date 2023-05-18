package com.store.gamestore.common;

import com.google.common.primitives.Doubles;
import com.store.gamestore.common.ApplicationConstants.UDF;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.spark.ml.linalg.DenseVector;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.api.java.UDF3;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SparkUDFRegisterer {

  private final SparkSession sparkSession;

  @PostConstruct
  void getRandomStringUDF() {
    UDF1<Integer, String> generateRandomString = len -> {
      final var ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";
      var builder = new StringBuilder();
      while (len-- > 0) {
        var index = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
        builder.append(ALPHA_NUMERIC_STRING.charAt(index));
      }
      return builder.toString();
    };

    sparkSession.udf().register(UDF.GENERATE_RANDOM_STRING, generateRandomString,
        DataTypes.StringType);
  }

  @PostConstruct
  void calculateJSDUDF() {
    sparkSession.udf().register("calculateJSD",
        (UDF2<DenseVector, DenseVector, Double>) SparkUDFRegisterer::calculateJSD,
        DataTypes.DoubleType);
  }

  @PostConstruct
  void normalizeValuesUDF() {
    sparkSession.udf().register("normalize",
        (UDF3<Double, Double, Double, Double>) SparkUDFRegisterer::normalize,
        DataTypes.DoubleType);
  }

  @PostConstruct
  void getMaxValueUDF() {
    sparkSession.udf().register("maxTopicIndex",
        (UDF1<DenseVector, Integer>) SparkUDFRegisterer::maxTopicIndex,
        DataTypes.IntegerType);
  }

  public static Double calculateJSD(DenseVector dist1, DenseVector dist2) {
    var p = dist1.toArray();
    var q = dist2.toArray();

    var m = new double[p.length];
    for (int i = 0; i < p.length; i++) {
      m[i] = (p[i] + q[i]) / 2.0;
    }
    return Math.sqrt((klDivergence(p, m) + klDivergence(q, m)) / 2.0);
  }

  public static double klDivergence(double[] p, double[] q) {
    double klDivergence = 0.0;
    for (int i = 0; i < p.length; i++) {
      klDivergence += p[i] * Math.log(p[i] / q[i]);
    }
    return klDivergence;
  }
  public static double normalize(double value, double max, double min) {
    return (value - min) / (max - min);
  }

  private static Integer maxTopicIndex(DenseVector values) {
    List<Double> list = Doubles.asList(values.toArray());
    return list.indexOf(Collections.max(list));
  }
}
