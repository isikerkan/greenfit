package ch.pa5.greenfit.service;

import ch.pa5.greenfit.model.SlotWithCalories;
import ch.pa5.greenfit.repository.ArticleRepository;
import ch.pa5.greenfit.repository.ConsumptionRepository;
import ch.pa5.greenfit.repository.SlotRepository;
import ch.pa5.greenfit.repository.entity.ArticleEntity;
import ch.pa5.greenfit.repository.entity.ConsumptionEntity;
import ch.pa5.greenfit.repository.entity.NutritionalValuesEntity;
import ch.pa5.greenfit.repository.entity.PortionEntity;
import ch.pa5.greenfit.repository.entity.PortionSizeEntity;
import ch.pa5.greenfit.repository.entity.SlotEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalorieService {

  private final EntityManager entityManager;
  private final ArticleRepository articleRepository;
  private final ConsumptionRepository consumptionRepository;
  private final SlotRepository slotRepository;

  public List<ArticleEntity> getAllCalories() {
    return articleRepository.findAll();
  }

  public List<ConsumptionEntity> getAllConsumptions(Long userId) {
    return consumptionRepository.findAllByUserId(userId);
  }

  @Transactional
  public Optional<ConsumptionEntity> saveConsumption(ConsumptionEntity consumptionLogEntity) {
    val savedConsumption = consumptionRepository.save(consumptionLogEntity);
    entityManager.refresh(savedConsumption);
    return consumptionRepository.findById(savedConsumption.getId());
  }

  public List<ConsumptionEntity> getAllConsumptions(Long userId, LocalDate date) {
    val endOfDay = date.atTime(23, 59, 59, 999).toInstant(ZoneOffset.UTC);
    val startOfDay = date.atStartOfDay().toInstant(ZoneOffset.UTC);
    return consumptionRepository.findAllByUserIdAndDateBetween(userId, startOfDay, endOfDay);
  }

  private List<SlotEntity> getAllSlots() {
    return slotRepository.findAll();
  }

  /**
   * Sums up all the calories of a given list of consumptions. Summing works by taking the consumed
   * portion and multiplying it with the calories per 100g for the given food / article
   *
   * @param consumptionEntities List of the consumptions to be summed up
   * @return The total amount of calories for the given list of consumptions
   */
  public BigDecimal calculateCalories(List<ConsumptionEntity> consumptionEntities) {
    if (consumptionEntities == null) {
      return BigDecimal.ZERO;
    }
    return consumptionEntities.stream()
        .map(
            consumptionEntity -> {
              val portionSize =
                  Optional.ofNullable(consumptionEntity.getPortion())
                      .map(PortionEntity::getSize)
                      .map(PortionSizeEntity::getAmount)
                      .orElseGet(
                          () -> {
                            log.warn(
                                "ConsumationEntity with id {} does not have a portion size.",
                                consumptionEntity.getId());
                            return BigDecimal.ONE;
                          });
              val caloriesPer100g =
                  Optional.ofNullable(consumptionEntity.getArticle())
                      .map(ArticleEntity::getNutritionalValues)
                      .map(NutritionalValuesEntity::getCalories)
                      .orElse(1);
              return portionSize
                  .multiply(BigDecimal.valueOf(caloriesPer100g))
                  .multiply(new BigDecimal("0.01"));
            })
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(0, RoundingMode.FLOOR);
  }

  /**
   * Groups the input consumptions into their respective slots. Consumptions with no slot will
   * default to the 99th slot
   *
   * @param consumptions List of consumptions that should be grouped
   * @return A list of Records holding a slot and a list of consumptions for that slot
   */
  public List<SlotWithCalories> groupConsumptionsBySlot(List<ConsumptionEntity> consumptions) {
    val consumptionBySlot =
        consumptions.stream()
            .collect(
                Collectors.groupingBy(
                    consumption ->
                        Optional.ofNullable(consumption.getSlot())
                            .map(SlotEntity::getId)
                            .orElse(99L)));

    log.trace("consumption map: {}", consumptionBySlot);

    return getAllSlots().stream()
        .map(
            slotEntity ->
                new SlotWithCalories(
                    slotEntity, calculateCalories(consumptionBySlot.get(slotEntity.getId())), consumptionBySlot.getOrDefault(slotEntity.getId(),List.of()))
            )
        .sorted(Comparator.comparing(slotWithCalories -> slotWithCalories.slot().getId()))
        .toList();
  }

  public ArticleEntity getArticle(Long articleId) {
    return articleRepository.findById(articleId).orElseThrow();
  }

  @Transactional
  public void deleteUserdata(Long userId) {
    consumptionRepository.deleteAllByUserId(userId);
  }
}
