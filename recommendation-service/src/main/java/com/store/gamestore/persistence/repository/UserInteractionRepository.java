package com.store.gamestore.persistence.repository;

import com.store.gamestore.persistence.entity.InteractionType;
import com.store.gamestore.persistence.entity.UserInteraction;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserInteractionRepository extends MongoRepository<UserInteraction, UUID> {

  List<UserInteraction> findAllByUserId(UUID userId);

  @Aggregation(pipeline = {
      "{ $match: { 'userId' : ?0, 'interactionType' : ?1, 'recommended' : ?2 }}"})
  List<UserInteraction> findAllUserInteractions(UUID userId, InteractionType interactionType,
      Boolean recommended);

  List<UserInteraction> findAllByGameId(UUID gameId);

  @Aggregation(pipeline = {
      "{ $match: { 'gameId' : ?0, 'interactionType' : ?1, 'recommended' : ?2 } }",
      "{ $count:  'gameInteractionCount'}"})
  Optional<Integer> countAllGameInteractions(UUID gameId, InteractionType interactionType,
      Boolean recommended);

  @Aggregation(pipeline = {"{ $match: { 'gameId' : ?0, 'interactionType' : ?1 } }",
      "{ $count:  'gameInteractionCount'}"})
  Optional<Integer> countAllGameInteractions(UUID gameId, InteractionType interactionType);

  @Aggregation(pipeline = {
      "{ $match: { 'gameId' : ?0, 'interactionType' : ?1, date:{$gte:ISODate(?2),$lt:ISODate(?3)}} }",
      "{ $count:  'gameInteractionCount'}"})
  Optional<Integer> countAllGameInteractionsByDate(UUID gameId, InteractionType interactionType,
      LocalDate start, LocalDate end);

  @Aggregation(pipeline = {"{ $match: { 'recommenderName' : ?0, 'interactionType' : ?1 } }",
      "{ $count:  'recommenderInteractionCount'}"})
  Optional<Integer> countAllRecommenderInteractions(String recommenderName,
      InteractionType interactionType);

  @Aggregation(pipeline = {
      "{ $match: { 'userId' : ?0, 'interactionType' : ?1, 'recommended' : ?2  } }",
      "{ $count:  'userInteractionCount'}"})
  Optional<Integer> countAllUserInteractions(UUID userId, InteractionType interactionType,
      Boolean recommended);

  @Aggregation(pipeline = {
      "{ $match: { 'gameId' : ?0, 'interactionType' : ?1, 'recommended' : ?2 } }"})
  List<UserInteraction> getAllGameInteractions(UUID gameId, InteractionType interactionType,
      Boolean recommended);

  @Query(value = "{ 'userId' : ?0, 'gameId' : ?1, 'interactionType' : ?2, 'recommended' : ?3 }",
      exists = true)
  Optional<Boolean> userInteractionExists(UUID userId, UUID gameId,
      InteractionType interactionType, Boolean recommended);

  @Query(value = "{ 'recommenderName' : ?0, 'gameId' : ?1, 'interactionType' : ?2 }",
      exists = true)
  Optional<Boolean> recommenderInteractionExists(String recommender, UUID gameId,
      InteractionType interactionType);

  @Aggregation(pipeline = {"{ $match: { 'userId' : ?0 }}",
      "{ $group: { _id: \"$gameId\" }}"})
  List<UUID> getAllInteractedGamesByUserId(UUID userId);

  @Aggregation(pipeline = {"{ $match: { 'recommenderName' : ?0 }}",
      "{ $group: { _id: \"$gameId\" }}"})
  List<UUID> getAllInteractedGamesByRecommenderName(String recommenderName);

  List<UserInteraction> findAllByUserIdAndGameIdAndInteractionType(UUID userId, UUID gameId,
      InteractionType interactionType);

  void deleteAllByUserIdAndGameIdAndInteractionType(UUID userId, UUID gameId,
      InteractionType interactionType);

  @Aggregation(pipeline = {"{ $match: { 'userId' : ?0, 'interactionType' : ?1 }}",
      "{ $count: 'maxInteractions'}"})
  Integer countMaxUserInteractions(UUID userId, InteractionType interactionType);
}
