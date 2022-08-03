package cz.recipe.dto;

import cz.ingredient.dto.IngredientDto;
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
    private List<IngredientDto> ingredients;

    public RecipeDto(Recipe recipe) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.description = recipe.getDescription();
        List<IngredientDto> ingredientDtos = new ArrayList<>();
        recipe.getIngredients()
                .forEach(ingredient -> ingredientDtos.add(new IngredientDto(ingredient)));
        this.ingredients = ingredientDtos;
    }
}
