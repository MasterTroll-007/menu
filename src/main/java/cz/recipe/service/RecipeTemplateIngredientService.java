package cz.recipe.service;

import cz.repository.RecipeTemplateIngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecipeTemplateIngredientService implements IRecipeTemplateIngredientService {

    private final RecipeTemplateIngredientRepository recipeTemplateIngredientRepository;


    @Override
    public void removeRecipeTemplateIngredient(Long recipeIngredientId) {
        if(recipeTemplateIngredientRepository.findById(recipeIngredientId).isPresent())
            recipeTemplateIngredientRepository.deleteById(recipeIngredientId);
    }
}
