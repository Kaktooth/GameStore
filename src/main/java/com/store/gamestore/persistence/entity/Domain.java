package com.store.gamestore.persistence.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Domain implements Serializable {

  @Id
  @GeneratedValue
  private UUID id;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Domain domain = (Domain) o;
    return id != null && Objects.equals(id, domain.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
