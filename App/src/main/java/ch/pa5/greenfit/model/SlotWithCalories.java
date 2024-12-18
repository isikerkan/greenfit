package ch.pa5.greenfit.model;

import ch.pa5.greenfit.repository.entity.SlotEntity;
import java.math.BigDecimal;

public record SlotWithCalories(SlotEntity slot, BigDecimal calories) {}
