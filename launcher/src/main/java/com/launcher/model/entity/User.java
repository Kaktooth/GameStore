package com.launcher.model.entity;

import java.util.UUID;

public class User {

  private UUID id;
  private String username;
  private String password;
  private Boolean enabled;
  private String email;
  private String publicUsername;
  private String phoneNumber;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setPublicUsername(String publicUsername) {
    this.publicUsername = publicUsername;
  }

  public String getPublicUsername() {
    return this.publicUsername;
  }
}

