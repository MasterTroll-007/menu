package cz.recipe.dto;

import cz.recipe.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private List<RecipeIngredientDto> recipeIngredients;

    public RecipeDto(Recipe recipe) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.description = recipe.getDescription();
        List<RecipeIngredientDto> ingredientDtos = new ArrayList<>();
        recipe.getRecipeIngredients()
                .forEach(ingredient -> ingredientDtos.add(new RecipeIngredientDto(ingredient)));
        this.recipeIngredients = ingredientDtos;
    }
}
