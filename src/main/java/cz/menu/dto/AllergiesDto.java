package cz.menu.dto;

import cz.ingredient.dto.IngredientDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllergiesDto implements Serializable {
    private Long id;
    private String name;
    private List<IngredientDto> ingredients;
}
