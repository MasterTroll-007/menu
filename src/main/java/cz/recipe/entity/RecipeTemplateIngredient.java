package cz.recipe.entity;

import cz.menu.entity.AbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class RecipeTemplateIngredient extends AbstractPersistable implements Serializable {

    private Long ingredientId;

    private Long grams;

    @JoinColumn(name = "recipe_template_id")
    private Long recipeTemplateId;
}
