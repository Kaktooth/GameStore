package com.launcher.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class Game extends Domain {

  private String title;
  private BigDecimal price;
  private String developer;
  private String publisher;
  private List<Genre> genres;

  public BigDecimal getPrice() {
    return price;
  }

  public String getDeveloper() {
    return developer;
  }

  public List<Genre> getGenres() {
    return genres;
  }

  public String getPublisher() {
    return publisher;
  }

  public String getTitle() {
    return title;
  }

  public void setDeveloper(String developer) {
    this.developer = developer;
  }

  public void setGenres(List<Genre> genres) {
    this.genres = genres;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
