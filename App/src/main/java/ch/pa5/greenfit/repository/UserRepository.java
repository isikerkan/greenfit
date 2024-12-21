package ch.pa5.greenfit.repository;

import ch.pa5.greenfit.repository.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

  Optional<UserEntity> findByExternalId(String externalId);
}
