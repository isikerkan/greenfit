package ch.pa5.greenfit.repository;

import ch.pa5.greenfit.repository.entity.RecipeEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface RecipeRepository extends ListCrudRepository<RecipeEntity, Long> {

}
