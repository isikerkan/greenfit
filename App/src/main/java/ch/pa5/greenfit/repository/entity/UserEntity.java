package ch.pa5.greenfit.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
  @Id private Long id;

  private String external_id;

  private String email;
}
