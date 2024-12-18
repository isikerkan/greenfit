package ch.pa5.greenfit.repository;

import ch.pa5.greenfit.repository.entity.PortionEntity;
import ch.pa5.greenfit.repository.entity.RecipeEntity;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;

public interface PortionRepository extends ListCrudRepository<PortionEntity, Long> {

}
