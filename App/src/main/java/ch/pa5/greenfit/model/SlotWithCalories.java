package ch.pa5.greenfit.model;

import ch.pa5.greenfit.repository.entity.SlotEntity;
import java.math.BigDecimal;
import ch.pa5.greenfit.repository.entity.ConsumptionEntity;
import java.util.List;

public record SlotWithCalories(SlotEntity slot, BigDecimal calories, List<ConsumptionEntity> consumption) {
}
