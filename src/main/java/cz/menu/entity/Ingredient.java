package cz.menu.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Ingredient extends AbstractPersistable implements Serializable {

    private String name;

    private int proteins;

    private int carbohydrates;

    private int fats;

    private int fibre;

}
