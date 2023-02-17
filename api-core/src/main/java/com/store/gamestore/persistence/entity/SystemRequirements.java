package com.store.gamestore.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "system_requirements")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SystemRequirements extends Domain {

  @Column(name = "minimal_memory")
  private Integer minimalMemory;

  @Column(name = "recommended_memory")
  private Integer recommendedMemory;

  @Column(name = "minimal_storage")
  private Integer minimalStorage;

  @Column(name = "recommended_storage")
  private Integer recommendedStorage;

  @Column(name = "game_profile_id")
  private UUID gameProfileId;

  @Column(name = "minimal_processor_id")
  private Integer minimalProcessorId;

  @Column(name = "recommended_processor_id")
  private Integer recommendedProcessorId;

  @Column(name = "minimal_graphic_card_id")
  private Integer minimalGraphicCardId;

  @Column(name = "recommended_graphic_card_id")
  private Integer recommendedGraphicCardId;

  @Column(name = "minimal_operating_system_id")
  private Integer minimalOperatingSystemId;

  @Column(name = "recommended_operating_system_id")
  private Integer recommendedOperatingSystemId;

}
