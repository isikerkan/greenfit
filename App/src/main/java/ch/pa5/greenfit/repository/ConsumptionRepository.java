package ch.pa5.greenfit.repository;

import ch.pa5.greenfit.repository.entity.ConsumptionEntity;
import java.time.Instant;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;

public interface ConsumptionRepository extends ListCrudRepository<ConsumptionEntity, Long> {

  List<ConsumptionEntity> findAllByUserId(Long userId);

  List<ConsumptionEntity> findAllByUserIdAndDateBetween(Long userId, Instant from, Instant to);
}
