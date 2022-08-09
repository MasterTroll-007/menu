package cz.recipe.entity;

import cz.menu.entity.AbstractPersistable;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Recipe extends AbstractPersistable implements Serializable {

    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipeId")
    private List<RecipeIngredient> recipeIngredients;
}
