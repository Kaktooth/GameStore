package com.store.gamestore.model.dto;


import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class GamePurchaseDTO {

  private BigDecimal amount;

  private Date purchaseDate;

  private String userId;

  private String gameId;

}
