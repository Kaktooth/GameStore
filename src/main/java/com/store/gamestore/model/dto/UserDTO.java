package com.store.gamestore.model.dto;


import java.io.Serializable;
import lombok.Data;

@Data
public class UserDTO implements Serializable {

  private String username;

  private String password;

  private Boolean enabled;

  private String email;

  private String phoneNumber;

}
