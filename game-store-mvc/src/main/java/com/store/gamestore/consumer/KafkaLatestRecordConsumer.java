package com.store.gamestore.consumer;

import java.util.UUID;

public interface KafkaLatestRecordConsumer<T> {

  T getRecord(UUID key);
}
