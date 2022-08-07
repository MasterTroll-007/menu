package cz.recipe.dto;

import cz.recipe.entity.RecipeIngredient;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientDto implements Serializable {

    private Long id;
    private float grams;
    private Long ingredientId;


    public RecipeIngredientDto(RecipeIngredient recipeIngredient) {
        this.id = recipeIngredient.getId();
        this.grams = recipeIngredient.getGrams();
        this.ingredientId = recipeIngredient.getIngredientId();
    }
}
