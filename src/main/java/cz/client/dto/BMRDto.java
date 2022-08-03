package cz.client.dto;

import cz.client.entity.BMR;
import cz.client.entity.Client;
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

    public BMRDto(BMR bmr) {
        this.id = bmr.getId();
        this.kJ = bmr.getKJ();
        this.proteins = bmr.getProteins();
        this.carbohydrates = bmr.getCarbohydrates();
        this.fats = bmr.getFats();
        this.fibres = bmr.getFibres();
    }
}
