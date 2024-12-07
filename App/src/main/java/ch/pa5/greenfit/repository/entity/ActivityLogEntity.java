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
import java.math.BigInteger;
import lombok.Data;
import lombok.With;

@Data
@Entity
@Table(name = "activity_log")
public class ActivityLogEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name="user_id")
  private UserEntity user;

  @ManyToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "activity_id")
  private ActivityEntity activity;

  @Column(name = "duration_s")
  private BigInteger duration;
}