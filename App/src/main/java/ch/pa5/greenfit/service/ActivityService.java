package ch.pa5.greenfit.service;

import ch.pa5.greenfit.repository.ActivityLogRepository;
import ch.pa5.greenfit.repository.ActivityRepository;
import ch.pa5.greenfit.repository.ActivityTrackerRepository;
import ch.pa5.greenfit.repository.entity.ActivityEntity;
import ch.pa5.greenfit.repository.entity.ActivityLogEntity;
import ch.pa5.greenfit.repository.entity.ActivityTrackerEntity;
import ch.pa5.greenfit.repository.entity.ActivityTrackerEntity.ActivityTimerType;
import ch.pa5.greenfit.repository.entity.ConsumptionEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

  private final EntityManager entityManager;
  private final ActivityRepository activityRepository;
  private final ActivityLogRepository activityLogRepository;
  private final ActivityTrackerRepository activityTrackerRepository;

  public List<ActivityEntity> getAllActivities() {
    return activityRepository.findAll();
  }

  public List<ActivityLogEntity> getAllActivityLogs(Long userId) {
    return activityLogRepository.findAllByUserId(userId);
  }

  @Transactional
  public Optional<ActivityLogEntity> saveActivityLog(ActivityLogEntity activityLogEntity) {
    val savedActivity = activityLogRepository.save(activityLogEntity);
    entityManager.refresh(activityLogEntity);
    return activityLogRepository.findById(savedActivity.getId());
  }

  public Optional<ActivityEntity> findActivityLog(Long activityLogId) {
    return activityRepository.findById(activityLogId);
  }

  public Optional<ActivityTrackerEntity> findActiveTracker(Long userId) {
    return activityTrackerRepository.findByUserIdAndStopUtcIsNull(userId);
  }

  public ActivityTrackerEntity startActivityTimer(ActivityTrackerEntity activityTrackerEntity) {
    val activeTracker =
        activityTrackerRepository.findByUserIdAndStopUtcIsNull(
            activityTrackerEntity.getUser().getId());
    if (activeTracker.isPresent()) {
      throw new ResponseStatusException(
          HttpStatusCode.valueOf(400), "Cannot start timer. Another tracker is already active.");
    }
    return activityTrackerRepository.save(
        activityTrackerEntity.withType(ActivityTimerType.TIMER).withStartUtc(Instant.now()));
  }

  @Transactional
  public ActivityTrackerEntity stopActivityTimer(Long userId) {
    val active =
        activityTrackerRepository
            .findByUserIdAndStopUtcIsNull(userId)
            .filter(tracker -> tracker.getType() == ActivityTimerType.TIMER)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatusCode.valueOf(400), "Cannot stop a tracker that does not exist!"));
    activityTrackerRepository.save(active.withStopUtc(Instant.now()));
    entityManager.flush();
    entityManager.refresh(active);
    return activityTrackerRepository.findById(active.getId()).orElseThrow();
  }

  public List<ActivityLogEntity> getAllActivities(Long userId, LocalDate date) {
    val endOfDay = date.atTime(23, 59, 59, 999).toInstant(ZoneOffset.UTC);
    val startOfDay = date.atStartOfDay().toInstant(ZoneOffset.UTC);
    return activityLogRepository.findAllByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay);
  }

  public BigDecimal calculateTotalBurn(Long userId, LocalDate date) {
    val activities = getAllActivities(userId, date);
    return activities.stream()
        .map(
            activityLogEntity -> {
              val metScore = activityLogEntity.getActivity().getMetScore();
              val duration = activityLogEntity.getDuration();
              val bodyWeight = activityLogEntity.getUser().getOptions().getWeight();

              val metFactor =
                  metScore
                      .multiply(new BigDecimal("3.5"))
                      .multiply(BigDecimal.valueOf(bodyWeight))
                      .multiply(new BigDecimal("0.005"));
              log.debug(
                  "{} - bodyweight {} metScore {} MetFactor: {}",
                  activityLogEntity.getActivity().getName(),
                  bodyWeight,
                  metScore,
                  metFactor);

              val durationInMin = duration.divide(new BigInteger("60"));
              log.debug("Duration in minutes: {}", durationInMin);

              return metFactor.multiply(new BigDecimal(durationInMin));
            })
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
