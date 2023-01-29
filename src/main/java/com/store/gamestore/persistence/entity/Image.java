package com.store.gamestore.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

@Entity
@Data
@Table(name = "images")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Image extends Domain {

  @Column(name = "image", nullable = false)
  byte[] imageData;

  public String getBase64ImageData() {
    return Base64.encodeBase64String(imageData);
  }
}
