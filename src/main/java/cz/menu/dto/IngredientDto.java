package cz.menu.dto;

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
    private int proteins;
    private int carbohydrates;
    private int fats;
    private int fibre;
}
