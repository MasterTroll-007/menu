package cz.recipe.service;

import cz.recipe.dto.RecipeTemplateDto;
import cz.recipe.entity.RecipeTemplate;
import cz.recipe.exception.RecipeException;
import org.springframework.data.domain.Page;

public interface IRecipeService {
    RecipeTemplate addRecipe(RecipeTemplateDto recipeDto);
    RecipeTemplateDto findById(Long recipeId) throws RecipeException;
    RecipeTemplateDto updateRecipeForm(RecipeTemplateDto recipeDto, Long recipeId) throws RecipeException;
    void deleteRecipe(Long recipeId);
    Page<RecipeTemplateDto> findPaginated(int currentPage, int pageSize, String sortField, String sortDir);

}
