package ch.pa5.greenfit.service;

import ch.pa5.greenfit.repository.ActivityLogRepository;
import ch.pa5.greenfit.repository.ActivityRepository;
import ch.pa5.greenfit.repository.RecipeRepository;
import ch.pa5.greenfit.repository.entity.ActivityEntity;
import ch.pa5.greenfit.repository.entity.ActivityLogEntity;
import ch.pa5.greenfit.repository.entity.RecipeEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

  private final EntityManager entityManager;
  private final ActivityRepository activityRepository;
  private final ActivityLogRepository activityLogRepository;

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
}
