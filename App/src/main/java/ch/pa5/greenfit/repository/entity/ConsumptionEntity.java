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
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "consumption")
public class ConsumptionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "recipe_id")
  private RecipeEntity recipe;

  @ManyToOne
  @JoinColumn(name = "article_id")
  private ArticleEntity article;

  @ManyToOne
  @JoinColumn(name = "slots_id")
  private SlotEntity slot;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "portion_id")
  private PortionEntity portion;

  private Instant date;

  @CreationTimestamp private Instant createdAt;
}
