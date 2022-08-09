package cz.recipe.dto;

import cz.recipe.entity.RecipeTemplateIngredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeTemplateIngredientDto implements Serializable {

    private Long id;
    private float grams;
    private Long ingredientId;


    public RecipeTemplateIngredientDto(RecipeTemplateIngredient recipeTemplateIngredient) {
        this.id = recipeTemplateIngredient.getId();
        this.grams = recipeTemplateIngredient.getGrams();
        this.ingredientId = recipeTemplateIngredient.getIngredientId();
    }
}
