package com.store.gamestore.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

  @Id
  private UUID id;
  @Column(name = "username", nullable = false)
  private String username;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "enabled", nullable = false)
  private Boolean enabled;
  @Column(name = "email", nullable = false)
  private String email;
  @Column(name = "public_username", nullable = false)
  private String publicUsername;
  @Column(name = "phone_number")
  private String phoneNumber;
}
