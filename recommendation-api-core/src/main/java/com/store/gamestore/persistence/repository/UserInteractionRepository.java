package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.UserInteraction;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserInteractionRepository extends MongoRepository<UserInteraction, String> {

  List<UserInteraction> findAllByUserId(String userId);

  @Aggregation(pipeline = {
      "{ $match: { 'userId' : ?0, 'interactionType' : ?1, 'recommended' : ?2 }}"})
  List<UserInteraction> findAllUserInteractions(String userId,
      InteractionType interactionType, Boolean recommended);

  @Aggregation(pipeline = {
      "{ $match: { 'gameId' : ?0, 'interactionType' : ?1 } }",
      "{ $count:  'gameInteractionCount'}"})
  Integer countAllGameInteractions(UUID gameId, InteractionType interactionType);

  @Aggregation(pipeline = {
      "{ $match: { 'gameId' : ?0, 'interactionType' : ?1, 'recommended' : ?2 } }"})
  List<UserInteraction> getAllGameInteractions(UUID gameId, InteractionType interactionType,
      Boolean recommended);

  @Aggregation(pipeline = {
      "{ $match: { 'userId' : ?0, 'gameId' : ?1, 'recommended' : ?3 }}",
      "{ 'interactionType' : {$in: [?1]}}"})
  Boolean userInteractionExists(String userId,
      String gameId, InteractionType interactionType, Boolean recommended);

  @Aggregation(pipeline = {"{ $match: { 'userId' : ?0 }}",
      "{ $group: { _id: \"$gameId\" }}"})
  List<UUID> getAllInteractedGamesByUserId(String userId);

  List<UserInteraction> findAllByUserIdAndGameIdAndInteractionType(String userId, String gameId,
      InteractionType interactionType);

  void deleteAllByUserIdAndGameIdAndInteractionType(String userId, String gameId,
      InteractionType interactionType);

  @Aggregation(pipeline = {
      "{ $match: { 'userId' : ?0, 'interactionType' : ?1 }}",
      "{ $count: 'maxInteractions'}"})
  Integer countMaxUserInteractions(String userId, InteractionType interactionType);
}
