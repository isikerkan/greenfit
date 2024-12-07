@import ch.pa5.greenfit.repository.entity.RecipeEntity
@import java.util.List

@param List<RecipeEntity> recipes

@for(RecipeEntity recipe: recipes)
    <option value="${recipe.getId()}">${recipe.getName()}</option>
@endfor
