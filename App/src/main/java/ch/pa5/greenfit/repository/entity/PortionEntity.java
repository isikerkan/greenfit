package ch.pa5.greenfit.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "portion")
public class PortionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "portion_size_id")
  private PortionSizeEntity size;

  @ManyToOne
  @JoinColumn(name = "rezept_id")
  private RecipeEntity recipe;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;
}
