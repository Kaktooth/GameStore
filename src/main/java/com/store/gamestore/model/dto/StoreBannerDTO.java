package com.store.gamestore.model.dto;


import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class StoreBannerDTO implements Serializable {

  private String userId;

  private String gameId;

  private UUID imageId;

  private String bannerDescription;

}
