package com.store.gamestore.model.util;

import org.springframework.core.io.ByteArrayResource;

public interface MetricsExporter {

  public ByteArrayResource exportUserMetrics();

  public ByteArrayResource exportRecommenderMetrics();

  public ByteArrayResource exportUploadedGamesMetrics();
}
