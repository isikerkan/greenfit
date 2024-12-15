package ch.pa5.greenfit.repository;

import ch.pa5.greenfit.repository.entity.UserEntity;
import ch.pa5.greenfit.repository.entity.UserOptionEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserOptionRepository extends CrudRepository<UserOptionEntity, Long> {

}
