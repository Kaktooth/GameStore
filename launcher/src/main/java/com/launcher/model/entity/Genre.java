package com.launcher.model.entity;


public class Genre {

  private Integer id;
  private String name;

  public void setName(String name) {
    this.name = name;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
