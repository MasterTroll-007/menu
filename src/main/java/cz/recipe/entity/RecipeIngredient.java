package cz.recipe.entity;

import cz.menu.entity.AbstractPersistable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class RecipeIngredient extends AbstractPersistable implements Serializable {

    private Long ingredientId;

    private Long grams;
}
