package ch.pa5.greenfit.repository;

import ch.pa5.greenfit.repository.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

}
