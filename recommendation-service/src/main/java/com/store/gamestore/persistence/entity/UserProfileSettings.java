package com.store.gamestore.persistence.entity;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@Document(collection = "user-profile-settings")
public class UserProfileSettings {

  @MongoId
  private UUID id;
  private UUID userId;
  private Integer minFixedPrice;
  private Integer maxFixedPrice;
  private LocalDate afterReleaseFilter;
}
