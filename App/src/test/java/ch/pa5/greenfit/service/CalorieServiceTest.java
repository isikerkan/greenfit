package ch.pa5.greenfit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import ch.pa5.greenfit.model.SlotWithCalories;
import ch.pa5.greenfit.repository.SlotRepository;
import ch.pa5.greenfit.repository.entity.ArticleEntity;
import ch.pa5.greenfit.repository.entity.ConsumptionEntity;
import ch.pa5.greenfit.repository.entity.NutritionalValuesEntity;
import ch.pa5.greenfit.repository.entity.PortionEntity;
import ch.pa5.greenfit.repository.entity.PortionSize;
import ch.pa5.greenfit.repository.entity.PortionSizeEntity;
import ch.pa5.greenfit.repository.entity.SlotEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CalorieServiceTest {

  @Autowired private CalorieService calorieService;

  @MockBean private SlotRepository slotRepository;

  @Test
  void calculate_calories() {
    val consumption =
        ConsumptionEntity.builder()
            .portion(
                PortionEntity.builder()
                    .size(
                        PortionSizeEntity.builder()
                            .amount(new BigDecimal("350"))
                            .type(PortionSize.GRAMM)
                            .build())
                    .build())
            .article(
                ArticleEntity.builder()
                    .nutritionalValues(NutritionalValuesEntity.builder().calories(200).build())
                    .build())
            .build();
    val consumption2 =
        ConsumptionEntity.builder()
            .portion(
                PortionEntity.builder()
                    .size(
                        PortionSizeEntity.builder()
                            .amount(new BigDecimal("500"))
                            .type(PortionSize.GRAMM)
                            .build())
                    .build())
            .article(
                ArticleEntity.builder()
                    .nutritionalValues(NutritionalValuesEntity.builder().calories(460).build())
                    .build())
            .build();

    assertEquals(new BigDecimal("700"), calorieService.calculateCalories(List.of(consumption)));

    assertEquals(
        new BigDecimal("3000"),
        calorieService.calculateCalories(List.of(consumption, consumption2)));
  }

  @Test
  void calculate_calories_empty() {
    assertEquals(BigDecimal.ZERO, calorieService.calculateCalories(null));
    assertEquals(BigDecimal.ZERO, calorieService.calculateCalories(List.of()));
  }

  @Test
  void group_by_slot_default() {
    val consumption =
        ConsumptionEntity.builder()
            .portion(
                PortionEntity.builder()
                    .size(
                        PortionSizeEntity.builder()
                            .amount(new BigDecimal("350"))
                            .type(PortionSize.GRAMM)
                            .build())
                    .build())
            .article(
                ArticleEntity.builder()
                    .nutritionalValues(NutritionalValuesEntity.builder().calories(200).build())
                    .build())
            .build();
    val consumption2 =
        ConsumptionEntity.builder()
            .portion(
                PortionEntity.builder()
                    .size(
                        PortionSizeEntity.builder()
                            .amount(new BigDecimal("500"))
                            .type(PortionSize.GRAMM)
                            .build())
                    .build())
            .article(
                ArticleEntity.builder()
                    .nutritionalValues(NutritionalValuesEntity.builder().calories(460).build())
                    .build())
            .build();

    val defaultSlot = SlotEntity.builder().id(99L).build();
    val otherSlot = SlotEntity.builder().id(1L).build();
    when(slotRepository.findAll()).thenReturn(List.of(otherSlot, defaultSlot));

    val grouped = calorieService.groupConsumptionsBySlot(List.of(consumption, consumption2));

    assertEquals(2, grouped.size());
    assertEquals(new SlotWithCalories(otherSlot, BigDecimal.ZERO, List.of()), grouped.getFirst());
    assertEquals(new SlotWithCalories(defaultSlot, new BigDecimal("3000"), List.of()), grouped.getLast());
  }

  @Test
  void group_by_slot() {
    val consumption =
        ConsumptionEntity.builder()
            .portion(
                PortionEntity.builder()
                    .size(
                        PortionSizeEntity.builder()
                            .amount(new BigDecimal("350"))
                            .type(PortionSize.GRAMM)
                            .build())
                    .build())
            .article(
                ArticleEntity.builder()
                    .nutritionalValues(NutritionalValuesEntity.builder().calories(200).build())
                    .build())
            .slot(SlotEntity.builder().id(1L).build())
            .build();
    val consumption2 =
        ConsumptionEntity.builder()
            .portion(
                PortionEntity.builder()
                    .size(
                        PortionSizeEntity.builder()
                            .amount(new BigDecimal("500"))
                            .type(PortionSize.GRAMM)
                            .build())
                    .build())
            .article(
                ArticleEntity.builder()
                    .nutritionalValues(NutritionalValuesEntity.builder().calories(460).build())
                    .build())
            .slot(SlotEntity.builder().id(2L).build())
            .build();

    val defaultSlot = SlotEntity.builder().id(99L).build();
    val firstSlot = SlotEntity.builder().id(1L).build();
    val secondSlot = SlotEntity.builder().id(2L).build();
    when(slotRepository.findAll()).thenReturn(List.of(firstSlot, secondSlot, defaultSlot));

    val grouped = calorieService.groupConsumptionsBySlot(List.of(consumption, consumption2));

    assertEquals(3, grouped.size());
    assertEquals(new SlotWithCalories(firstSlot, new BigDecimal("700"), List.of()), grouped.getFirst());
    assertEquals(new SlotWithCalories(secondSlot, new BigDecimal("2300"), List.of()), grouped.get(1));
    assertEquals(new SlotWithCalories(defaultSlot, BigDecimal.ZERO, List.of()), grouped.getLast());
  }
}
