package ch.pa5.greenfit.repository;

import ch.pa5.greenfit.repository.entity.ActivityLogEntity;
import ch.pa5.greenfit.repository.entity.RecipeEntity;
import java.time.Instant;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;

public interface ActivityLogRepository extends ListCrudRepository<ActivityLogEntity, Long> {

  List<ActivityLogEntity> findAllByUserId(Long userId);
  List<ActivityLogEntity> findAllByUserIdAndCreatedAtBetween(Long userId, Instant from, Instant to);
}
