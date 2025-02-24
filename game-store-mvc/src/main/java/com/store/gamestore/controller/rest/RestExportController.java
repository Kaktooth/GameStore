package com.store.gamestore.controller.rest;

import com.store.gamestore.model.util.MetricsExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/export")
@RequiredArgsConstructor
public class RestExportController {

  private final MetricsExporter metricsExporter;

  @GetMapping("/user-metrics")
  public ResponseEntity<ByteArrayResource> exportUserMetrics() {

    ByteArrayResource bytes = metricsExporter.exportUserMetrics();

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expenses.xlsx");

    return ResponseEntity.ok()
        .headers(headers)
        .contentLength(bytes.contentLength())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(bytes);
  }

  @GetMapping("/recommender-metrics")
  public ResponseEntity<ByteArrayResource> exportRecommenderMetrics() {

    ByteArrayResource bytes = metricsExporter.exportRecommenderMetrics();

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expenses.xlsx");

    return ResponseEntity.ok()
        .headers(headers)
        .contentLength(bytes.contentLength())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(bytes);
  }

  @GetMapping("/game-metrics")
  public ResponseEntity<ByteArrayResource> exportUploadedGamesMetrics() {

    ByteArrayResource bytes = metricsExporter.exportUploadedGamesMetrics();

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expenses.xlsx");

    return ResponseEntity.ok()
        .headers(headers)
        .contentLength(bytes.contentLength())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(bytes);
  }
}
