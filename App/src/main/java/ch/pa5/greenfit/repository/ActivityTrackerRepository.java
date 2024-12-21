package ch.pa5.greenfit.repository;

import ch.pa5.greenfit.repository.entity.ActivityTrackerEntity;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

public interface ActivityTrackerRepository extends ListCrudRepository<ActivityTrackerEntity, Long> {

  Optional<ActivityTrackerEntity> findByUserIdAndStopUtcIsNull(Long userId);

  void deleteAllByUserId(Long userId);

}
