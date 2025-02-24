package com.store.gamestore.model.dto;


import lombok.Data;

@Data
public class PaymentInfoDTO {

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
