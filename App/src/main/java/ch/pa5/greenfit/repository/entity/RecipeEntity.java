package ch.pa5.greenfit.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rezept")
public class RecipeEntity {
  @Id private Long id;
  private String name;
}
