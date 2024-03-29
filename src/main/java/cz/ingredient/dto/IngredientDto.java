package cz.ingredient.dto;

import cz.ingredient.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto implements Serializable {

    private Long id;
    private String name;
    private Long proteins;
    private Long carbohydrates;
    private Long fats;
    private Long fibre;
    private Long kJ;

    public IngredientDto(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.proteins = ingredient.getProteins();
        this.carbohydrates = ingredient.getCarbohydrates();
        this.fats = ingredient.getFats();
        this.fibre = ingredient.getFibre();
        this.kJ = ingredient.getKJ();
    }

}
