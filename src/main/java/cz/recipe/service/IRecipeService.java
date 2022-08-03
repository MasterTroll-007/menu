package cz.recipe.service;

import cz.recipe.dto.RecipeDto;
import cz.recipe.exception.RecipeException;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public interface IRecipeService {
    void addRecipe(RecipeDto recipeDto);
    RecipeDto updateRecipeForm(Model model, Long recipeId) throws RecipeException;
    void deleteRecipe(Long recipeId);
    Page<RecipeDto> findPaginated(int currentPage, int pageSize, String sortField, String sortDir);

}
