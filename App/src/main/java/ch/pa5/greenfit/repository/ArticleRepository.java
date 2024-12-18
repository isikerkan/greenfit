package ch.pa5.greenfit.repository;

import ch.pa5.greenfit.repository.entity.ArticleEntity;
import ch.pa5.greenfit.repository.entity.RecipeEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ArticleRepository extends ListCrudRepository<ArticleEntity, Long> {

}
