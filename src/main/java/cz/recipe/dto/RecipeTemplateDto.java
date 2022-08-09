package cz.recipe.dto;

import cz.recipe.entity.RecipeTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeTemplateDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private List<RecipeTemplateIngredientDto> recipeTemplateIngredients;

    public RecipeTemplateDto(RecipeTemplate recipeTemplate) {
        this.id = recipeTemplate.getId();
        this.name = recipeTemplate.getName();
        this.description = recipeTemplate.getDescription();

        List<RecipeTemplateIngredientDto> ingredientDtos = new ArrayList<>();
        recipeTemplate.getRecipeTemplateIngredients()
                .forEach(ingredient -> ingredientDtos.add(new RecipeTemplateIngredientDto(ingredient)));
        this.recipeTemplateIngredients = ingredientDtos;
    }
}
