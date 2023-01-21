package com.store.gamestore.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "purchase_history")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GamePurchase extends Domain {

  @Column(name = "money_amount", nullable = false)
  private BigDecimal moneyAmount;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Column(name = "user_id")
  private UUID userId;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "game_id")
  private Game game;
}
