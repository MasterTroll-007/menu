package cz.ingredient.dto;

import cz.ingredient.entity.Ingredient;
import lombok.Data;

import java.io.Serializable;

@Data
public class IngredientDto implements Serializable {

    private Long id;
    private String name;
    private int proteins;
    private int carbohydrates;
    private int fats;
    private int fibre;
    private int kJ;

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
