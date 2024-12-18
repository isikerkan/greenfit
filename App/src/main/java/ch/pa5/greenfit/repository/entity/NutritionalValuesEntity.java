package ch.pa5.greenfit.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "nutritional_values")
@ToString(exclude = {"article"})
public class NutritionalValuesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "calories_kcal")
  private Integer calories;

  @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name="article_id")
  private ArticleEntity article;
}
