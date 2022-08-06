package cz.recipe.entity;

import cz.menu.entity.AbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Recipe extends AbstractPersistable implements Serializable {

    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RecipeIngredient> recipeIngredients;
}
