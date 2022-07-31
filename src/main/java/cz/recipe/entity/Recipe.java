package cz.recipe.entity;

import cz.ingredient.entity.Ingredient;
import cz.menu.entity.AbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Recipe extends AbstractPersistable implements Serializable {

    private String name;

    private String description;
    @ManyToMany
    private List<Ingredient> ingredients;
}
