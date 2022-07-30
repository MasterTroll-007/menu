package cz.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BMRDto implements Serializable {

    private Long id;
    private double kJ;
    private double proteins;
    private double carbohydrates;
    private double fats;
    private double fibres;
}
