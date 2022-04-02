package com.store.gamestore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GamePurchase extends Purchase {
    private UserGame userGame;

    public GamePurchase(BigDecimal amount, LocalDateTime date, UserGame userGame) {
        super(amount, date);
        this.userGame = userGame;
    }
}
