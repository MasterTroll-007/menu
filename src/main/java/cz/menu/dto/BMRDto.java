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
    private int kJ;
    private int proteins;
    private int carbohydrates;
    private int fats;
    private int fibres;
}
