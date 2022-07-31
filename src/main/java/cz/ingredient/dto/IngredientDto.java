package cz.ingredient.dto;

import com.fasterxml.jackson.annotation.JsonView;
import cz.ingredient.entity.Ingredient;
import lombok.Data;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.io.Serializable;

@Data
public class IngredientDto implements Serializable {

    @JsonView(DataTablesOutput.View.class)
    private Long id;
    @JsonView(DataTablesOutput.View.class)
    private String name;
    @JsonView(DataTablesOutput.View.class)
    private int proteins;
    @JsonView(DataTablesOutput.View.class)
    private int carbohydrates;
    @JsonView(DataTablesOutput.View.class)
    private int fats;
    @JsonView(DataTablesOutput.View.class)
    private int fibre;

    public IngredientDto(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.proteins = ingredient.getProteins();
        this.carbohydrates = ingredient.getCarbohydrates();
        this.fats = ingredient.getFats();
        this.fibre = ingredient.getFibre();
    }

}
