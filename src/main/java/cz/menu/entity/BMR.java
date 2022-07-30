package cz.menu.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Setter
@Getter
public class BMR extends AbstractPersistable implements Serializable {

    private double kJ;

    private double proteins;

    private double carbohydrates;

    private double fats;

    private double fibres = 30;
}
