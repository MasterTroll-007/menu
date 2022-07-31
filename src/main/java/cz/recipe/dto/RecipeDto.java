package cz.recipe.dto;

import cz.ingredient.dto.IngredientDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private List<IngredientDto> ingredients;
}
