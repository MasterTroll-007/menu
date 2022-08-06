package cz.recipe.dto;

import cz.menu.entity.AbstractPersistable;
import cz.recipe.entity.RecipeIngredient;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientDto extends AbstractPersistable implements Serializable {

    private Long id;
    private Long grams;
    private Long ingredientId;


    public RecipeIngredientDto(RecipeIngredient recipeIngredient) {
        this.id = recipeIngredient.getId();
        this.grams = recipeIngredient.getGrams();
        this.ingredientId = recipeIngredient.getIngredientId();
    }
}
