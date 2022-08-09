package cz.recipe.entity;

import cz.menu.entity.AbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class RecipeTemplate extends AbstractPersistable implements Serializable {

    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipeTemplateId", orphanRemoval = true)
    private List<RecipeTemplateIngredient> recipeTemplateIngredients;
}
