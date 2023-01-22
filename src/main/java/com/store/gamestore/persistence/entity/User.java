package com.store.gamestore.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends Domain {

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    User user = (User) o;
    return getId() != null && Objects.equals(getId(), user.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
