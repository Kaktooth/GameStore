package com.store.gamestore.model.util;

import com.store.gamestore.persistence.entity.CalculatedMetric;
import com.store.gamestore.service.metrics.MetricsService;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.util.comparator.Comparators;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExcelMetricsExporter implements MetricsExporter {

  private final UserHolder userHolder;
  private final MetricsService metricsService;

  public ByteArrayResource exportUserMetrics() {
    final var userMetrics = metricsService.getCalculatedUserMetrics();
    return export(userMetrics);

  }

  @Override
  public ByteArrayResource exportRecommenderMetrics() {
    final var recommenderMetrics = metricsService.getCalculatedRecommenderMetrics();
    return export(recommenderMetrics);
  }

  @Override
  public ByteArrayResource exportUploadedGamesMetrics() {
    final var user = userHolder.getAuthenticated();
    final var uploadedGamesMetrics = metricsService.getCalculatedGamesMetrics(user.getId());
    return export(uploadedGamesMetrics);
  }

  private ByteArrayResource export(Map<?, List<CalculatedMetric>> data) {

    Workbook workbook = new HSSFWorkbook();

    Sheet sheet = workbook.createSheet("Metrics Data");

    Map<Integer, Object[]> exportData = new TreeMap<>(Comparators.comparable());
    exportData.put(1, new Object[]{"MetricName", "Value"});
    int counter = 2;
    for (var entry : data.entrySet()) {
      exportData.put(counter,
          new Object[]{entry.getKey().toString()});
      counter = counter + 1;
      for (var exportObject : entry.getValue()) {
        final var cellValues = new Object[]{exportObject.getMetricName(),
            exportObject.getValue().toString()};
        exportData.put(counter, cellValues);
        counter = counter + 1;
      }
    }
    log.info("Export: exporting to excel format");
    Set<Integer> keyset = exportData.keySet();
    int rownum = 0;
    for (Integer key : keyset) {
      Row row = sheet.createRow(rownum++);
      Object[] objArr = exportData.get(key);
      int cellnum = 0;
      for (Object obj : objArr) {
        Cell cell = row.createCell(cellnum++);
        if (obj instanceof String) {
          cell.setCellValue((String) obj);
        } else if (obj instanceof Integer) {
          cell.setCellValue((Integer) obj);
        }
        log.info("Export: " + cell);
      }
    }

    try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {
      byte[] bytes;
      workbook.write(byteArray);

      bytes = byteArray.toByteArray();
      log.info("Export: completed");
      return new ByteArrayResource(bytes);
    } catch (Exception e) {
      log.info("Export: failed");
      e.printStackTrace();
      return null;
    }
  }
}

