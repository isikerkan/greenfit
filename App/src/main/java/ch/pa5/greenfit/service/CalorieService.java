package ch.pa5.greenfit.service;

import ch.pa5.greenfit.repository.PortionRepository;
import ch.pa5.greenfit.repository.RecipeRepository;
import ch.pa5.greenfit.repository.entity.PortionEntity;
import ch.pa5.greenfit.repository.entity.RecipeEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalorieService {

  private final EntityManager entityManager;
  private final RecipeRepository recipeRepository;
  private final PortionRepository portionRepository;

  public List<RecipeEntity> getAllCalories() {
    return recipeRepository.findAll();
  }

  public List<PortionEntity> getAllPortions(Long userId) {
    return portionRepository.findAllByUserId(userId);
  }

  @Transactional
  public Optional<PortionEntity> savePortion(PortionEntity calorieLogEntity) {
    val savedPortion = portionRepository.save(calorieLogEntity);
    entityManager.refresh(savedPortion);
    return portionRepository.findById(savedPortion.getId());
  }
}
