package com.store.gamestore.model.dto;


import java.util.UUID;
import lombok.Data;

@Data
public class UserPictureDTO {

  private UUID userId;

  private UUID imageId;

}
