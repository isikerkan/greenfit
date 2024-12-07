package ch.pa5.greenfit.repository;

import ch.pa5.greenfit.repository.entity.ActivityEntity;
import ch.pa5.greenfit.repository.entity.ActivityLogEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ActivityRepository extends ListCrudRepository<ActivityEntity, Long> {

}
