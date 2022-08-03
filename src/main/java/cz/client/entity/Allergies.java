package cz.client.entity;

import cz.ingredient.entity.Ingredient;
import cz.menu.entity.AbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Setter
@Getter
public class Allergies extends AbstractPersistable implements Serializable {

    private String name;

    @OneToMany
    private List<Ingredient> ingredients;
}
