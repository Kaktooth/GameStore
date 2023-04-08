package com.store.gamestore.service.recommendation;

import java.util.UUID;

public interface KafkaLatestRecordConsumer<T> {

  T getRecord(String topic, UUID key);
}
