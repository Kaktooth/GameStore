package com.store.gamestore.persistence.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "user-profile")
public class UserRecommenderProfile {

  @MongoId
  private String userId;
  private String recommenderName;
  private Integer recommenderRating;
}
