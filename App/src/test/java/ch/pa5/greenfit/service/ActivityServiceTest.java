package ch.pa5.greenfit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import ch.pa5.greenfit.repository.ActivityLogRepository;
import ch.pa5.greenfit.repository.entity.ActivityEntity;
import ch.pa5.greenfit.repository.entity.ActivityLogEntity;
import ch.pa5.greenfit.repository.entity.PersonEntity;
import ch.pa5.greenfit.repository.entity.UserEntity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.Validator;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ActivityServiceTest {

  @Autowired private ActivityService activityService;

  @MockBean private ActivityLogRepository activityLogRepository;

  private final UserEntity user =
      UserEntity.builder().options(PersonEntity.builder().weight(80).build()).build();
  @Autowired private Validator validator;

  @Test
  void calculate_burn() {
    val dummyActivity = ActivityEntity.builder().metScore(new BigDecimal("6.9")).build();
    val activityLog =
        ActivityLogEntity.builder()
            .activity(dummyActivity)
            .duration(new BigInteger("1200"))
            .user(user)
            .build();

    assertEquals(
        new BigDecimal("193.2"), activityService.calculateBurn(activityLog).stripTrailingZeros());
  }

  @Test
  void calculate_total_burn() {
    val now = LocalDate.now();

    val dummyActivity = ActivityEntity.builder().metScore(new BigDecimal("6.9")).build();
    val dummyActivity2 = ActivityEntity.builder().metScore(new BigDecimal("3")).build();
    val activityLog =
        ActivityLogEntity.builder()
            .activity(dummyActivity)
            .duration(new BigInteger("1200"))
            .user(user)
            .build();
    val activityLog2 =
        ActivityLogEntity.builder()
            .activity(dummyActivity2)
            .duration(new BigInteger("3000"))
            .user(user)
            .build();

    when(activityLogRepository.findAllByUserIdAndCreatedAtBetween(eq(1L), any(), any()))
        .thenReturn(List.of(activityLog, activityLog2));

    assertEquals(new BigDecimal("403.2"), activityService.calculateTotalBurn(1L, now).stripTrailingZeros());
  }
}
