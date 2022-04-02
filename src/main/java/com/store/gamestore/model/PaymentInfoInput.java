package com.store.gamestore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PaymentInfoInput {
    private String method;
    private String cardNumber;
    private Integer month;
    private Integer year;
    private Integer securityCode;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String state;
    private Integer zip;
}
